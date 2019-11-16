import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PlacesListInterface extends Remote {
    void addPlace(Place var1) throws RemoteException;

    ArrayList allPlaces() throws RemoteException;

    Place getPlace(String var1) throws RemoteException;
}