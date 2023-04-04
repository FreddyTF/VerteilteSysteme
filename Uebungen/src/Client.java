/**
     * This class is used as an example for a Client Socket
     * this could be a own thread
     */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private Node entryPoint;
    private String ownIpAdress;
    private ClientToEntryPoint ctep;

    /*
    * Starts initializing itself right away, contacts its entrypoint for this.
    */
    public Client(Node entryPoint, String ownIpAdress){
        this.entryPoint = entryPoint;
        this.ownIpAdress = ownIpAdress;
        this.initialize();
    }

    public void initialize(){
        this.ctep = new ClientToEntryPoint(this.entryPoint, this.ownIpAdress);
        this.ctep.run();
        this.sendMessage(this.entryPoint.getIp());
    }

    public void sendMessage(String message_as_string){
        Message message = new Message("Client", this.entryPoint.getIp(), message_as_string, MessageType.WRITE);
        String response = this.ctep.sendMessage(message);
    }   

}
