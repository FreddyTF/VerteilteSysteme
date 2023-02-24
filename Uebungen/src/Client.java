/**
     * This class is used as an example for a Client Socket
     * this could be a own thread
     */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public Socket myClient;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public void initialize(String dns, int port){
        try{
            this.myClient = new Socket(dns, port);
            OutputStream outputStream = this.myClient.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = this.myClient.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
        }
        catch (IOException e){
            System.out.println("Client tut net");
        }
    }

    public void sendMessage(String messageString){
        try{
            this.dataOutputStream.writeUTF(messageString);
            this.dataOutputStream.flush();

            String resp = dataInputStream.readUTF();
            System.out.println("Response: " + resp);

        }
        catch (IOException e){
            System.out.println("Client send not working");
        }
    }   

}
