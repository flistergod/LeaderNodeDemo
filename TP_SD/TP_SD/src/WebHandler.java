
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Esta é a classe do Web Service
//É o seu controller
//O index.xhtml é a sua view
//Para cada Input é feito um método
//Os métodos são muito parecidos, mas não podem ser reutilizáveis, pois trabalham com variáveis diferentes

@Named(value = "webHandler")
@SessionScoped
public class WebHandler implements Serializable {
    private  String port, port1, port2="", port3="",
            answer="",knownPMs="", knownPlaces="", livingTime="", leader="";
    int PORT_WEB_RECEIVER = 5001, PORT_WEB_ANSWER=5002;
    String GROUP_WEB_RECEIVER = "225.1.2.2", GROUP_WEB_ANSWER="225.1.2.4";

    public WebHandler() {
    }

    public String getPort3() {
        return port3;
    }

    public void setPort3(String port3) {
        this.port3 = port3;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getKnownPMs() {
        return knownPMs;
    }

    public void setKnownPMs(String knownPMs) {
        this.knownPMs = knownPMs;
    }

    public String getKnownPlaces() {
        return knownPlaces;
    }

    public void setKnownPlaces(String knownPlaces) {
        this.knownPlaces = knownPlaces;
    }

    public String getLivingTime() {
        return livingTime;
    }

    public void setLivingTime(String livingTime) {
        this.livingTime = livingTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort1() {
        return port1;
    }

    public void setPort1(String port1) {
        this.port1 = port1;
    }

    public String getPort2() {
        return port2;
    }

    public void setPort2(String port2) {
        this.port2 = port2;
    }

    public void sendPort() throws IOException, InterruptedException {
        //Envia por multicast ao lider a string msg
        //com o identificador de mensagem - Search e a porta do PM
        //este depois ficará à espera da resposta do lider
        //no final, é descontruída a resposta, para apresentar no htlm
       String msg=getPort() + " SEARCH";
       String[]split;

        InetAddress group = InetAddress.getByName(GROUP_WEB_RECEIVER);
        MulticastSocket multicastSocket = new MulticastSocket(PORT_WEB_RECEIVER);
        multicastSocket.joinGroup(group);
        DatagramPacket packet;
        packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT_WEB_RECEIVER);
        multicastSocket.send(packet);
        multicastSocket.close();

        group = InetAddress.getByName(GROUP_WEB_ANSWER);
        multicastSocket = new MulticastSocket(PORT_WEB_ANSWER);
        multicastSocket.joinGroup(group);

        byte[] buffer = new byte[100];
        packet = new DatagramPacket(buffer, buffer.length);
        multicastSocket.receive(packet);
        String answer= new String(buffer, 0, buffer.length);
        multicastSocket.close();

        split = answer.trim().split(";");

            if(split[0].equals("VALID")){

                    this.answer=split[1];
            this.livingTime = "Living time: " + split[2];
            this.knownPMs = "Known Place Managers: " + split[3];
            this.knownPlaces = "Known Places: " + split[4];
            this.leader="Leader: " + split[5];

            }else{
                this.answer="Invalid port";
                this.livingTime = "";
                this.knownPMs = "";
                this.knownPlaces = "";
                this.leader="";
            }

    }

    public void sendPort1() throws IOException {
        //Envia por multicast ao lider a string msg
        //com o identificador de mensagem - CREATE e a porta do PM
        //este depois ficará à espera da resposta do lider
        //no final, é descontruída a resposta, para apresentar no htlm
        String msg=getPort1() + " CREATE";
        String[]split;

        InetAddress group = InetAddress.getByName(GROUP_WEB_RECEIVER);
        MulticastSocket multicastSocket = new MulticastSocket(PORT_WEB_RECEIVER);
        multicastSocket.joinGroup(group);
        DatagramPacket packet;
        packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT_WEB_RECEIVER);
        multicastSocket.send(packet);
        multicastSocket.close();

        group = InetAddress.getByName(GROUP_WEB_ANSWER);
        multicastSocket = new MulticastSocket(PORT_WEB_ANSWER);
        multicastSocket.joinGroup(group);

        byte[] buffer = new byte[100];
        packet = new DatagramPacket(buffer, buffer.length);
        multicastSocket.receive(packet);
        String answer= new String(buffer, 0, buffer.length);
        multicastSocket.close();

        split = answer.trim().split(";");

        if(split[0].equals("VALID")){

            this.answer=split[1];
            this.livingTime = "";
            this.knownPMs = "";
            this.knownPlaces = "";
            this.leader="";

        }else{
            this.answer="Invalid location";
            this.livingTime = "";
            this.knownPMs = "";
            this.knownPlaces = "";
            this.leader="";
        }




    }

    public void sendPort2() throws IOException {
        //Envia por multicast ao lider a string msg
        //com o identificador de mensagem - insert e a porta do PM
        //este depois ficará à espera da resposta do lider
        //no final, é descontruída a resposta, para apresentar no htlm
        String msg=getPort2() + " INSERT " + getPort3();
        String[]split;


        InetAddress group = InetAddress.getByName(GROUP_WEB_RECEIVER);
        MulticastSocket multicastSocket = new MulticastSocket(PORT_WEB_RECEIVER);
        multicastSocket.joinGroup(group);
        DatagramPacket packet;
        packet = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT_WEB_RECEIVER);
        multicastSocket.send(packet);
        multicastSocket.close();

        group = InetAddress.getByName(GROUP_WEB_ANSWER);
        multicastSocket = new MulticastSocket(PORT_WEB_ANSWER);
        multicastSocket.joinGroup(group);

        byte[] buffer = new byte[100];
        packet = new DatagramPacket(buffer, buffer.length);
        multicastSocket.receive(packet);
        String answer= new String(buffer, 0, buffer.length);
        multicastSocket.close();

        split = answer.trim().split(";");

        if(split[0].equals("VALID")){

            this.answer=split[1];
            this.livingTime = "";
            this.knownPMs = "";
            this.knownPlaces = "";
            this.leader="";

        }else{
            this.answer="Invalid location";
            this.livingTime = "";
            this.knownPMs = "";
            this.knownPlaces = "";
            this.leader="";
        }

    }


}

