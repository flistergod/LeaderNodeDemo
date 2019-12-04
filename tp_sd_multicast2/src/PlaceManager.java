import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlaceManager extends MulticastSocket implements PlacesListInterface, Serializable, Remote {
    ArrayList<Place> myPlaces = new ArrayList();
 static final   ArrayList<Integer> ports = new ArrayList();
    static final HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();

    protected PlaceManager(int i) throws IOException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      addPM(String.valueOf(i));

       Thread t1 = (new Thread() {
            public void run() {
                try {
                    InetAddress group = InetAddress.getByName("225.4.5.6");
                    MulticastSocket multicastSocket = new MulticastSocket(3456);
                    multicastSocket.joinGroup(group);

                    byte[ ] buffer = new byte[100];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    multicastSocket.receive(packet);
                    String msg ="Place Manager shared his port: " + new String(buffer);

                    addPM(new String(buffer).trim());

                  // System.out.println(msg);
                    multicastSocket.close();

                }

                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        t1.start();
    }

    public void addPM(String port) throws RemoteException{
        if(!hmap.containsValue(Integer.parseInt(port)) && !hmap.containsKey(hmap.size())){
            hmap.put(hmap.size(),Integer.parseInt(port));

           // System.out.println("Inseriu o port: "+ port);

            try {
                InetAddress group = InetAddress.getByName("225.4.5.6");
                MulticastSocket multicastSocket = new MulticastSocket();
                String msg = port;

                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, 3456);
                multicastSocket.send(packet);
                multicastSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static int getEleitor(){
        int maior= hmap.get(0);

        Iterator<Map.Entry<Integer, Integer>> it = hmap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            //System.out.println(entry.getValue());
            if (entry.getValue() >maior) {
               maior=entry.getValue();
            }
        }


        return (maior);


    }


    public void removePM(String port) {


        Iterator<Map.Entry<Integer, Integer>> it = hmap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();

            // Remove entry if key is null or equals 0.
            if (entry.getValue()==Integer.parseInt(port)) {
                it.remove();
            }
        }
       ;


    }

    public void addPlace(Place p) throws RemoteException {
        this.myPlaces.add(p);
    }

    public ArrayList allPlaces() throws RemoteException {
        return this.myPlaces;
    }

    public Place getPlace(String objectID) throws RemoteException {
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