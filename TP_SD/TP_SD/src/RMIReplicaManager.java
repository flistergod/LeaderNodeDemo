import javax.rmi.CORBA.Util;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class RMIReplicaManager {
   static Registry r = null;
    private static PlaceManager placeList;

    public static int main(String[] args) {
int aux=0;
        try {
            r = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
        } catch (RemoteException aux4) {
            aux=1;
        }
        try {
            if(aux==0) {
                placeList = new PlaceManager(Integer.parseInt(args[0].trim()));
                r.rebind("placelist", placeList);
                System.out.println("Place Manager ready");
            }
        } catch (Exception aux3) {
            System.out.println("Place server main: " + aux3.getMessage());
        }
        return aux;
    }
}
