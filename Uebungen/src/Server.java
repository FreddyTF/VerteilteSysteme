import java.net.*;
import java.io.*;


public class Server implements Runnable{
    
    // this max client definition is not available in "old "
    // implementations
    public static final int maxIncomingClients = 100;
    public Socket myServer;
    /**
    * this method initialises the server
    * @param dns name like " localhost "
    * @param port port to use
    * @return the created socket after client connected
    * @throws IOException
    * @throws UnknownHostException
    */
    public void initialise(String dns, int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port,maxIncomingClients,InetAddress.getByName(dns));
            this.myServer = serverSocket.accept();
        }
        catch (IOException e){
            System.out.println("Server tut net"); 
        }
    }

    public void run(){
        this.readMessage();
    }

    public void readMessage(){
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(this.myServer.getInputStream());
            String toPrint = (String) ois.readObject();
            System.out.println(toPrint);
        }
        catch (IOException e){
            System.out.println("Server read not working");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
