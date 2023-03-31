import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FollowerToLeader extends Thread{

    private Node leader;
    private Node parentNode;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;

    public FollowerToLeader(Node leader, Node parentNode){
        this.leader = leader;
        this.parentNode = parentNode;
    }

    public void run(){
        Socket leaderSocket;
        try {
            leaderSocket = new Socket(leader.getIp(), leader.getPort());
            OutputStream outputStream = leaderSocket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = leaderSocket.getInputStream();
            this.dataInputStream = new DataInputStream(inputStream);
            System.out.println(this.parentNode.getIp() + ": connected successfully to leader: " + leader.getIp());
        } catch (IOException e) {
             System.out.println(e.toString());
            System.out.println(this.parentNode.getIp() + ": connecting to leader failed");
        }
       
    }

    public String sendMessage(Message message){
        try{
            this.objectOutputStream.writeObject(message);
            this.objectOutputStream.flush();

            String resp = dataInputStream.readUTF();
            System.out.println("Response: " + resp);
            return resp;
        }
        catch (IOException e){
            System.out.println("Client send not working by " + this.parentNode.getIp());
            return "failure";
        }
    }   
}