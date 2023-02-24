/**
     * This class is used as an example for a Client Socket
     * this could be a own thread
     */

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client implements Runnable{
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

    public void initialise(String dns, int port){
        try{
            this.myClient = new Socket(dns, port);
        }
        catch (IOException e){
            System.out.println("Client tut net");
        }
    }

    public void run(){
        this.sendMessage("test hallo 123");
    }

    public void sendMessage(String messageString){
        try{
            this.myClient.getOutputStream();
        }
        catch (IOException e){
            System.out.println("Client send not working");
        }
    }   

}
