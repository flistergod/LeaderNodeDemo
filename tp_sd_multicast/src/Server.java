import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {

        Registry r = null;

        try {
            r = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
        } catch (RemoteException aux4) {
            aux4.printStackTrace();
        }

        try {

            PlaceManager placeList = new PlaceManager(Integer.parseInt(args[0]));
            r.rebind("placelist", placeList);
            System.out.println("Place server ready");
        } catch (Exception aux3) {
            System.out.println("Place server main: " + aux3.getMessage());
        }

    }
}