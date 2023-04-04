import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientToEntryPoint extends Thread{

    private Node entryPoint;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private String ownIpAdress;

    public ClientToEntryPoint(Node entryPoint, String ownIpAdress){
        this.entryPoint = entryPoint;
        this.ownIpAdress = ownIpAdress;
    }

    public void run(){
        Socket entryPointSocket;
        try {
            entryPointSocket = new Socket(entryPoint.getIp(), entryPoint.getPort());
            OutputStream outputStream = entryPointSocket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = entryPointSocket.getInputStream();
            this.dataInputStream = new DataInputStream(inputStream);

            System.out.println("Client " + this.ownIpAdress +" connected successfully to entryPoint: " + entryPoint.getIp());
        } catch (IOException e) {
             System.out.println(e.toString());
            System.out.println("Client connecting to entryPoint failed");
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
            System.out.println("Client send not working by" + this.ownIpAdress);
            return "failure";
        }
    }   
}