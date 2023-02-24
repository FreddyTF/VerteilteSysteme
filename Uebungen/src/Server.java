import java.net.*;
import java.io.*;


public class Server{
    
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

    public void initialize(String dns, int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
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

    public void readMessage(){
        try{
            InputStream inputStream = this.myClient.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = myClient.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            while (!myClient.isClosed()){
                String msg = dataInputStream.readUTF();
                System.out.println("Incoming msg: " + msg);

                dataOutputStream.writeUTF("200");
            }
        }
        catch (IOException e){
            System.out.println("Server read not working");
        }
    }
}
