import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.AlreadyBoundException;

public class RMIClient {

    public static void main(String[] args) {

        //Neste sistema, é sempre inicializado algum PM
        //neste caso lançamos 2, para verificar a eleição do lider

        Thread t1 = (new Thread() {
            public void run() {

                    RMIReplicaManager.main(new String[]{"2018"});
                    RMIReplicaManager.main(new String[]{"2019"});
                 // RMIReplicaManager.removePm("2019");

                }



        });
        t1.start();

    }
}
