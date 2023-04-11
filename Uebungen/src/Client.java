/**
     * This class is used as an example for a Client Socket
     * this could be a own thread
     */

import java.net.Socket;

public class Client{
    /**
    * this method initialises the client
    * @param dns distination like " localhost "; can also be
    an IP
    * @param port port to connect to
    * @return the created socket after connection is
    established
     * @throws IOException
    */

    public Socket myNode;
    private Node entryPoint;
    private String ownIpAdress;
    private ConnectToServerSocket ctss;

    /*
    * Starts initializing itself right away, contacts its entrypoint for this.
    */
    public Client(Node entryPoint, String ownIpAdress){
        this.entryPoint = entryPoint;
        this.ownIpAdress = ownIpAdress;
        this.initialize();
    }

    public void initialize(){
        NodeSaver nodeSaver = new NodeSaver(this.entryPoint.getIp(), this.entryPoint.getPort(), this.entryPoint.getRole());
        this.ctss = new ConnectToServerSocket(nodeSaver, this.ownIpAdress, null);
        this.ctss.run();
    }

    public void sendMessage(String message_as_string, MessageType type){
        Message message = new Message("Client", this.entryPoint.getIp(), message_as_string, type);
        String response = this.ctss.sendMessage(message);
        System.out.println("Client " + this.ownIpAdress + " received answer: " + response);
    }   

}
