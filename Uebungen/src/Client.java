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
    private ConnectToServerSocket ctep;

    /*
    * Starts initializing itself right away, contacts its entrypoint for this.
    */
    public Client(Node entryPoint, String ownIpAdress){
        this.entryPoint = entryPoint;
        this.ownIpAdress = ownIpAdress;
        this.initialize();
    }

    public void initialize(){
        this.ctep = new ConnectToServerSocket(this.entryPoint, this.ownIpAdress, null);
        this.ctep.run();
    }

    public void sendMessage(String message_as_string){
        Message message = new Message("Client", this.entryPoint.getIp(), message_as_string, MessageType.WRITE);
        String response = this.ctep.sendMessage(message);
        System.out.println("Client " + this.ownIpAdress + " received answer: " + response);
    }   

}
