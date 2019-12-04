import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {

    public static void main(String[] args) {

        Thread t1;

        t1 = (new Thread() {
            public void run() {
                RMIReplicaManager.main(new String[]{"2018"});
                RMIReplicaManager.main(new String[]{"2019"});
                RMIReplicaManager.main(new String[]{"2020"});;


                //adiciona pm
                RMIReplicaManager.main(new String[]{"2001"});;


               /**/ //remove pm
                try {
                    RMIReplicaManager.removePM("2020");
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }


            }
        });
        t1.start();





    }
}
