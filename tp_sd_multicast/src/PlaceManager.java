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
import java.util.Iterator;

public class PlaceManager extends MulticastSocket implements PlacesListInterface, Serializable, Remote {
    ArrayList<Place> myPlaces = new ArrayList();

    protected PlaceManager(int i) throws IOException {

        Thread t1;

        t1 = (new Thread() {
            public void run() {
                try {
                    InetAddress group = InetAddress.getByName("225.4.5.6");
                    MulticastSocket multicastSocket = new MulticastSocket(3456);
                    multicastSocket.joinGroup(group);

                    byte[ ] buffer = new byte[100];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    multicastSocket.receive(packet);
                    System.out.println(new String(buffer));
                    multicastSocket.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
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