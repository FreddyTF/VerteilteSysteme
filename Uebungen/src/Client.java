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
     * @throws UnknownHostException
    */

    public Socket myNode;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private String entryIp;
    private int entryPort;


    public Client(String entryIp, int entryPort){
        this.entryIp = entryIp;
        this.entryPort = entryPort;
        this.initialize();
    }

    public void initialize(){
        try{
            this.myNode = new Socket(this.entryIp, this.entryPort);
            OutputStream outputStream = this.myNode.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = this.myNode.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
        }
        catch (IOException e){
            System.out.println("Client can't establish data stream");
        }
    }

    public void sendMessage(String message_as_string){
        Message message = new Message("Client", this.entryIp, message_as_string, MesssageType.WRITE);
        try{
            this.objectOutputStream.writeObject(message);
            this.objectOutputStream.flush();

            String resp = dataInputStream.readUTF();
            System.out.println("Response: " + resp);

        }
        catch (IOException e){
            System.out.println("Client can't send message");
        }
    }   

}
