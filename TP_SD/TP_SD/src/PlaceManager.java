import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

public class PlaceManager extends MulticastSocket implements PlacesListInterface, Serializable, Remote {

    //Variáveis
    ArrayList<Place> myPlaces = new ArrayList();
   HashMap<String, String> places = new HashMap<String, String>();
    static final HashMap<Timestamp, Integer> hmap = new HashMap<Timestamp, Integer>();
    static final HashMap<Integer, Integer> eleitores = new HashMap<Integer, Integer>();
    int PORT = 5000, PORT_WEB_RECEIVER=5001, PORT_WEB_ANSWER=5002;
    String GROUP = "225.1.2.3", GROUP_WEB_RECEIVER="225.1.2.2", GROUP_WEB_ANSWER="225.1.2.4";
    String split[], msg;
    int LIDER=0, port;
    boolean PRIMEIRAMSG=false, informa=false;
    ArrayList<String> arrayList = new ArrayList<String>();



    protected  PlaceManager(int i) throws IOException {

        port = i;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //na thread 1 irá acontecer a comunicação dos Pms
        //na thread 2 o líder vai interagir como front-end com o cliente.
        Thread t1 = new Thread() {
            public void run() {

                try {

                    while(true) {

                        if(port==LIDER) {

                            Thread t2 = new Thread() {
                                public void run() {
                                    try {
                                        while (true) {

                                            //o lider recebe do controller do web service por multicast pedidos do cliente
                                            //este pedido vai ser identificado no switch e dado uma resposta

                                            InetAddress group = InetAddress.getByName(GROUP_WEB_RECEIVER);
                                            MulticastSocket multicastSocket = new MulticastSocket(PORT_WEB_RECEIVER);
                                            multicastSocket.joinGroup(group);
                                            DatagramPacket packet;

                                            byte[] buffer = new byte[100];
                                            packet = new DatagramPacket(buffer, buffer.length);
                                            multicastSocket.receive(packet);

                                            String msg2 = new String(buffer, 0, buffer.length).trim();
                                            String[] split2=msg2.split(" ");


                                            //existem 3 tipos de pedido:
                                            //CREATE -  cria um pm, se possível
                                            //SEARCH - devolve informações sobre um PM
                                            //INSERT - insere nos Pms um novo place

                                                switch (split2[1]){

                                                    case "CREATE":

                                                        //verifica se o PM a ser criado existe, caso exista, devolve essa mensagem
                                                        //no multicast mais abaixo
                                                        if(hmap.containsValue(Integer.parseInt(split2[0].trim()))) {
                                                            msg2 ="VALID;"+ "Port "+split2[0] + " already exists";
                                                        }
                                                        else{
                                                            try {
                                                                    //se não existir, cria o PM
                                                                    RMIReplicaManager.main(new String[]{split2[0].trim()});
                                                                msg2="VALID;"+"Port "+split2[0] + " created";

                                                            } catch (Exception aux3) {
                                                                msg2="INVALID";
                                                            }
                                                        }
                                                    break;

                                                    case "SEARCH":
                                                        //verifica se o PM existe
                                                        if(hmap.containsValue(Integer.parseInt(split2[0].trim()))) {

                                                            //Reúne as suas informações

                                                            String port = "Port " + split2[0] + ":";
                                                            String livingTime = "", knownPM = "", knownPlaces = "";

                                                            Map.Entry<Timestamp, Integer> entry = hmap.entrySet().iterator().next();

                                                            Iterator<Map.Entry<Timestamp, Integer>> it = hmap.entrySet().iterator();
                                                            long timestampMillis = System.currentTimeMillis();
                                                            long timestampSeconds = TimeUnit.MILLISECONDS.toSeconds(timestampMillis);

                                                            while (it.hasNext()) {
                                                                entry = it.next();
                                                                //System.out.println(entry.getValue());
                                                                if (entry.getValue() == Integer.parseInt(split2[0].trim())) {
                                                                    livingTime = String.valueOf(Long.valueOf(timestampSeconds - (entry.getKey().getTime() / 1000)));
                                                                    break;


                                                                }
                                                            }


                                                            it = hmap.entrySet().iterator();

                                                            while (it.hasNext()) {
                                                                entry = it.next();
                                                                //System.out.println(entry.getValue());
                                                                if (entry.getValue() == Integer.parseInt(split2[0].trim())) {
                                                                    knownPM = knownPMs();
                                                                  //  knownPlaces = knownPlaces();
                                                                    break;

                                                                }
                                                            }
                                                            //caso não tenha informações sobre os PMs e Localizações
                                                            if (knownPM == "") {
                                                                knownPM = "none";
                                                            }
                                                            if (knownPlaces == "") {
                                                                knownPlaces = "none";
                                                            }
                                                            //devolve esta mensagem no multicast mais abaixo
                                                            msg2 ="VALID;"+ port + ";" + livingTime + " seconds;" + knownPM + ";" + knownPlaces+";"+LIDER;
                                                        }


                                                        else{msg2="INVALID";}

                                                        break;
                                                    case "INSERT":

                                                        //Insere a localização se esta não existir e contrói a mensagem a devolver
                                                                if(!places.containsKey(Integer.parseInt(split2[2].trim()))){
                                                                    Place p= new Place(split2[2],split2[0]);
                                                                    myPlaces.add(p);
                                                                    places.put(split2[2],split2[0]);
                                                                msg2="VALID;"+split2[0]+" "+split2[2] + " created";}
                                                                else{msg2="Location already exists";}


                                                      //  String msg3="INSERT: "+split2[0].trim()+" "+split2[2].trim();

                                                        break;


                                                    default: msg2="INVALID"; break;

                                                }

                                          // System.out.println(msg2);

                                            multicastSocket.close();

                                                //o lider usa dois grupos de multicast
                                                //um para receber dados do web service
                                                //e outro para enviar para o web service
                                                //tentámos usar só um, mas havia muito spam de mensagens

                                             group = InetAddress.getByName(GROUP_WEB_ANSWER);
                                            multicastSocket = new MulticastSocket(PORT_WEB_ANSWER);
                                            multicastSocket.joinGroup(group);

                                            packet = new DatagramPacket(msg2.getBytes(), msg2.length(), group, PORT_WEB_ANSWER);
                                            multicastSocket.send(packet);
                                            multicastSocket.close();
                                        }

                                    } catch (Exception e) {
                                        //caso o líder falhe, este volta a ser criado
                                        System.out.println("Leader went down, will revive it");
                                        RMIReplicaManager.main(new String[]{String.valueOf(LIDER)});

                                    }

                                }
                            };
                            t2.start();
                        }

                        //Na thread 1, há sempre uma mensagem default a ser enviada por casa PM - a sua informação
                        //caso se queira enviar outra coisa, o informa fica a true, e a info desse PM, nesta iteração, não
                        //é enviada

                        //Todos os PMs têm um boolean PRIMEIRAMSG a verificar se é a primeira mensagem que vão enviar por multicast
                        //Se for, então é porque este acabou de ser criado e tem de se eleger o Lider
                        //É então enviada uma mensagem ELECTION para começar a eleição


                            if(informa==false){
                         msg= "INFO: This is my port: " + port;}


                        if(PRIMEIRAMSG==false){
                            msg="ELECTION: Number of PM changed... Port: "+ port + " will start election";
                            PRIMEIRAMSG=true;
                        }

                        InetAddress group = InetAddress.getByName(GROUP);
                        MulticastSocket multicastSocket = new MulticastSocket(PORT);
                        multicastSocket.joinGroup(group);
                        DatagramPacket packet;

                        packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
                        multicastSocket.send(packet);

                        byte[] buffer = new byte[100];
                        packet = new DatagramPacket(buffer, buffer.length);
                        multicastSocket.receive(packet);

                        msg = new String(buffer, 0, buffer.length);
                        split=msg.split(" ");

                        switch (split[0]){

                            //Caso algum PM receba a info de outro PM que não tenha na sua view, ese adiciona-o
                            case "INFO:":
                            if (!hmap.containsValue(Integer.parseInt(split[5].trim()))) {
                                hmap.put(new Timestamp(System.currentTimeMillis()), Integer.parseInt(split[5].trim()));
                            }
                           // System.out.println(msg);
                                informa=false;
                            break;


//Na election, os PMs adicionam o nome PM nas suas views e enviam uma mensagem ELECTOR, em que votam no líder (getMaior())
                            //Quando envia o seu voto por multicast, a votação vai começar, não pode enviar asua info
                            //e por isso o boolean informa fica a true
                            case "ELECTION:":
                                if (!hmap.containsValue(Integer.parseInt(split[6].trim()))) {
                                    hmap.put(new Timestamp(System.currentTimeMillis()), Integer.parseInt(split[6].trim()));
                                }
                                System.out.println(msg);


                                msg = "ELECTOR: Port: " + port + " elected " + getMaior() + " as leader";
                                packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
                                multicastSocket.send(packet);
                                informa=true;
                                break;


                                //Na mensagem Elector, são retirados o voto e quem votou
                            //Estes são colocados no hash eleitores, que pode ser vista como uma urna
                            //a urna antes de aceitar o voto, verifica se já o tem desse PM
                            //caso o tenha, vai alterar o seu voto

                            //no momento que a contagem dos votos for igual ao número de PMs
                            //  if(eleitores.size()==hmap.size()){
                            //A urna é "fechada" e é eleito o líder.
                            //escolhe-se o maior
                            //feita a escolha do líder, o seu valor é enviado por multicast

                            case "ELECTOR:":
                                split=msg.split(" ");

                                if(!eleitores.containsKey(Integer.parseInt(split[2].trim()))){
                                    eleitores.put(Integer.parseInt(split[2].trim()), Integer.parseInt(split[4].trim()));
                                }

                                if(eleitores.containsKey(Integer.parseInt(split[2].trim()))){
                                    Map.Entry<Integer, Integer> entry;

                                    Iterator<Map.Entry<Integer, Integer>> it = eleitores.entrySet().iterator();


                                    while (it.hasNext()) {
                                        entry = it.next();

                                        if(entry.getKey()== Integer.parseInt(split[2].trim())){
                                            entry.setValue(Integer.parseInt(split[4].trim()));
                                        }
                                    }
                                }

                                if(eleitores.size()==hmap.size()){

                                    /**ELEGER LIDER AQUI*/
                                    Map.Entry<Integer, Integer> entry = eleitores.entrySet().iterator().next();
                                    int maior=entry.getValue();

                                    Iterator<Map.Entry<Integer, Integer>> it = eleitores.entrySet().iterator();

                                    while (it.hasNext()) {
                                        entry = it.next();
                                        //System.out.println(entry.getValue());
                                        if (entry.getValue() >=maior) {
                                            maior=entry.getValue();
                                        }
                                    }

                                    msg="LEADER: The new leader is port: "+ maior;
                                    System.out.println(msg);
                                    packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
                                    multicastSocket.send(packet);
                                    }
                                break;



                            case "LEADER:":

                              LIDER= Integer.parseInt(split[6].trim());
                               // System.out.println(LIDER);

                                informa=false;
                                break;

                                //Este Insert serve para atualizar os places do PM
                            case "INSERT:":

                                Place p = new Place(split[1].trim(),split[2].trim());
                                if(!myPlaces.contains(p)){myPlaces.add(p);
                                System.out.println("Location inserted");}break;

                            default: System.out.println( "Unexpected multicast message received...");  break;

                        }
                }
                }
                catch (Exception e){
                    System.out.println("ERRO CONSTRUTOR");
                }

            }
        };
        t1.start();
    }
    //devolve o maior PM em termos de port na vista
    private synchronized String getMaior() {

        Map.Entry<Timestamp, Integer> entry = hmap.entrySet().iterator().next();
        int maior=entry.getValue();

        Iterator<Map.Entry<Timestamp, Integer>> it = hmap.entrySet().iterator();

        while (it.hasNext()) {
            entry = it.next();
            //System.out.println(entry.getValue());
            if (entry.getValue() >maior) {
                maior=entry.getValue();
            }
        }
        return String.valueOf(maior);
    }

//devolve os ports dos PMs da vista de um PM em string
    public  synchronized  String knownPMs(){

        String Pms = "";

            Map.Entry<Timestamp, Integer> entry = hmap.entrySet().iterator().next();

            Iterator<Map.Entry<Timestamp, Integer>> it = hmap.entrySet().iterator();

            while (it.hasNext()) {
                entry = it.next();

                //System.out.println(entry.getValue());
                Pms+=String.valueOf(entry.getValue())+", ";
            }
        return Pms;
    }

    //devolve os lugares de um PM numa string
    public  synchronized  String knownPlaces(){

        String Kps = "";

        Map.Entry<String, String> entry = places.entrySet().iterator().next();

        Iterator<Map.Entry<String, String>> it = places.entrySet().iterator();

        while (it.hasNext()) {
            entry = it.next();
            //System.out.println(entry.getValue());
            Kps+=String.valueOf(entry.getValue())+", ";
        }
        return Kps;
    }

//adiciona place
    public synchronized void addPlace(Place p) throws RemoteException {
        this.myPlaces.add(p);
    }
//retorna array de places
    public synchronized ArrayList allPlaces() throws RemoteException {
        return this.myPlaces;
    }
//retorna place dado o seu codigo postal
    public synchronized Place getPlace(String objectID) throws RemoteException {
        Iterator aux = this.myPlaces.iterator();

        Place p;
        do {
            if (!aux.hasNext()) {
                return null;
            }

            p = (Place)aux.next();
        } while(!p.getPostalCode().equals(objectID));

        return p;
    }


}