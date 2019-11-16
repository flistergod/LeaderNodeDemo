import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client{

    public static void main(String[] args) {

        Thread t1;

        t1 = (new Thread() {
            public void run() {
                Server.main(new String[]{"2020"});
                Server.main(new String[]{"2019"});
                Server.main(new String[]{"2018"});

            }
        });
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            InetAddress group = InetAddress.getByName("225.4.5.6");
            MulticastSocket multicastSocket = new MulticastSocket();
            String msg = "Hello World!";

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, 3456);
            multicastSocket.send(packet);
            multicastSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
