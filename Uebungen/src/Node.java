import java.net.*;
import java.util.LinkedList;
import java.io.*;


public class Node extends Thread{
    
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
    private String ip = "";
    private int port = -1;
    private int nodeId = -1;
    private Role role = Role.UNKNOWN;
    private LinkedList<Node> listOfNodes = new LinkedList<>();

    public Socket leader;
    public Socket follower;
    public Socket[] clients;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;

    public Node(Role role, String ip, int port){
        this.role = role;
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        //if follower -> init an go in while true for sending() and reading()
        if(this.role == Role.FOLLOWER){
            try{
                Node leader = this.getLeader();
                this.leader = new Socket(leader.getIp(), leader.getPort());
                OutputStream outputStream = this.leader.getOutputStream();
                this.objectOutputStream = new ObjectOutputStream(outputStream);
                InputStream inputStream = this.leader.getInputStream();
                this.dataInputStream = new DataInputStream(inputStream);
                System.out.println("Connected successfully to leader: " + leader.getIp());
            }
            catch (IOException e){
                System.out.println("Follower: connecting to leader failed");
            }

            Message message = new Message("MyClient", "MyServer", "payload " + 1, "Message");
            while(true){
                this.sendMessage(message);
            }
        }
        else if (this.role == Role.LEADER){
            //if leader -> init and go in while true for read_message() and send_message()
            try{
                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("leader accepting incoming messages from now on");
                this.follower = serverSocket.accept();
            }
            catch (IOException e){
                System.out.println("leader: Opening as a leader failed");
            }
            System.out.println("Reading messages from now on");
            this.readMessages(this.follower);
        }
    }

    public void closeSockets(){
        try {
            this.leader.close();
        } catch (Exception e) { 
            System.out.println("An error occured whilst closing the sockets.");
        }
    }

    public void readMessages(Socket connections){
        try{
            InputStream inputStream = connections.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = connections.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ObjectMessageReader omr = new ObjectMessageReader();
            omr.initInputStreams(connections);
            
            while (!connections.isClosed()){
                Message message = omr.read(connections);
                System.out.println("Incoming msg: " + message.getPayload());

                dataOutputStream.writeUTF("200");
            }
        }
        catch (IOException e){
            System.out.println("Server read not working");
        }
    }

    public void sendMessage(Message message){
        try{
            this.objectOutputStream.writeObject(message);
            this.objectOutputStream.flush();

            String resp = dataInputStream.readUTF();
            System.out.println("Response: " + resp);

        }
        catch (IOException e){
            System.out.println("Client send not working");
        }
    }   
    
    private Node getLeader(){
        for(Node node : this.listOfNodes){
            if(node.getRole() == Role.LEADER){
                return node;
            }
        }
        System.out.println("Fehler hier, kein leader in sicht");
        return null;
    }

    public LinkedList<Node> getListOfNodes(){return this.listOfNodes;}
    public void setListOfNodes(LinkedList<Node> list){this.listOfNodes = list;}
    public String getIp(){return this.ip;}
    public void setIp(String ip) {this.ip=ip;}
    public int getPort(){return this.port;}
    public void setPort(int port){this.port=port;}
    public int getNodeId(){return this.nodeId;}
    public void setNodeId(int id ){this.nodeId=id;}
    public Role getRole(){return this.role;}
    public void setRole(Role role){this.role = role;}
}
