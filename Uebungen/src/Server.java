import java.net.*;
import java.io.*;


public class Server implements Runnable{
    
    // this max client definition is not available in "old "
    // implementations
    /**
    * this method initialises the server
    * @param dns name like " localhost "
    * @param port port to use
    * @return the created socket after client connected
    * @throws IOException
    * @throws UnknownHostException
    */

    public static final int maxIncomingClients = 100;
    public ServerSocket myServer;
    public Socket myClient;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public void initialise(String dns, int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server now accepting");
            myClient = serverSocket.accept();
        }
        catch (IOException e){
            System.out.println("Server tut net"); 
        }
    }

    public void closeSockets(){
        try {
            myServer.close();
        } catch (Exception e) { 
            System.out.println("An error occured whilst closing the sockets.");
        }
    }

    public void run(){
        this.readMessage();
    }

    public void readMessage(){
        try{
            System.out.println("init server read");
            InputStream inputStream = this.myClient.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            System.out.println("middle server read");
            OutputStream outputStream = myClient.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            String toPrint = dataInputStream.readUTF();
            System.out.println("server has read");
            System.out.println("message: " + toPrint);
        }
        catch (IOException e){
            System.out.println("Server read not working");
        }
    }
}
