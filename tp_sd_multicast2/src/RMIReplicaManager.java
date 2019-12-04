import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RMIReplicaManager {
    static final   ArrayList<PlaceManager> placeManagers = new ArrayList();
    static final   ArrayList<Integer> ports = new ArrayList();
   static final HashMap<Integer, Integer> eleitores = new HashMap<Integer, Integer>();
   static Registry r = null;
    private static PlaceManager placeList;


    public static void main(String[] args) {



        try {
            r = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
        } catch (RemoteException aux4) {
            aux4.printStackTrace();
        }

        try {

            placeList = new PlaceManager(Integer.parseInt(args[0]));
            r.rebind("placelist", placeList);
            placeManagers.add(placeList);
            ports.add(Integer.parseInt(args[0]));
            System.out.println("Place Manager ready");
             elegeLider();

        } catch (Exception aux3) {
            System.out.println("Place server main: " + aux3.getMessage());
        }

    }



    public static void removePM(String port) throws RemoteException, NotBoundException {
int j=0;
        if(!ports.contains(Integer.parseInt(port))){
            System.out.println("Invalid port");
        }
        else {

            for(int i=0;i<ports.size();i++){
                if(ports.get(i)==Integer.parseInt(port)){
                    placeManagers.get(i).removePM(String.valueOf(ports.get(i)));
                    placeManagers.remove(placeManagers.get(i));
                    j=i;
                }
            }
            ports.remove(j);
            r.unbind("placelist");
            elegeLider();

        }
    }

    public static void elegeLider() throws RemoteException, NotBoundException {
        eleitores.clear();
        int eleitor=0;

        for(int i=0;i<placeManagers.size();i++){

            eleitor=placeManagers.get(i).getEleitor();
            eleitores.put(ports.get(i),eleitor);

        }



        int maior= eleitores.get(ports.get(0));

        Iterator<Map.Entry<Integer, Integer>> it = eleitores.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            //System.out.println(entry.getValue());
            if (entry.getValue()==maior) {
                maior=entry.getValue();
            }
            else System.out.println("Cant elect leader");break;
        }


        for(int i=0;i<placeManagers.size();i++){

            eleitor=placeManagers.get(i).getEleitor();
            eleitores.put(ports.get(i),eleitor);
            System.out.println("Port " + ports.get(i) + " elected port " + eleitor + " as leader");

        }
       System.out.println("The Place Manager leader is the Place Manager with port: " + String.valueOf(maior));


    }


    }
