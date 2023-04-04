import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectToServerSocket extends Thread{

    private Node destinationNode;
    private Node parentNode;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private String ownIpAddress;

    public ConnectToServerSocket(Node destinationNode, String ownIpAdress, Node parentNode){
        this.destinationNode = destinationNode;
        this.ownIpAddress = ownIpAdress;
        this.parentNode = parentNode;
    }

    public void run(){
        Socket destinationSocket;
        try {
            destinationSocket = new Socket(destinationNode.getIp(), destinationNode.getPort());
            if(this.parentNode != null){
                this.parentNode.leaderSocket = destinationSocket;
            }
            OutputStream outputStream = destinationSocket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = destinationSocket.getInputStream();
            this.dataInputStream = new DataInputStream(inputStream);

            System.out.println(this.ownIpAddress +" connected successfully to ServerSocket: " + destinationNode.getIp());
        } catch (IOException e) {
             System.out.println(e.toString());
            System.out.println("Connecting from " + this.ownIpAddress + " to ServerSocket " + destinationNode.getIp() + " failed");
        }
       
    }

    public String sendMessage(Message message){
        try{
            this.objectOutputStream.writeObject(message);
            this.objectOutputStream.flush();
            String resp = dataInputStream.readUTF();
            return resp;
        }
        catch (IOException e){
            System.out.println("Send to node not working by: " + this.ownIpAddress);
            return "failure";
        }
    }   
}