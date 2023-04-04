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

    public Socket leader;   //the one leader, if self is a leader -> empty
    public LinkedList<NodeSaver> connections = new LinkedList<NodeSaver>(); //all connected nodes
    public LinkedList<NodeSaver> allNodes = new LinkedList<NodeSaver>(); //all nodes, incl. own -> same list for every node
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private FollowerToLeader ftl;

    public Node(Role role, String ip, int port){
        this.role = role;
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        if(this.role == Role.FOLLOWER){
            this.run_follower();
        }
        else if (this.role == Role.LEADER){
            this.run_leader();
        }
    }

    public void run_leader(){
        //if leader -> init and go in while true for read_message() and send_message 
        try{
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress(this.ip, this.port);
            serverSocket.bind(address);
            while(true){
                NodeSaver newConnection = new NodeSaver(serverSocket.accept());
                this.initializeStreams(newConnection);
                this.connections.add(newConnection); // -> waiting for first follower to connect before continuing
                ReadMessageObject rmo = new ReadMessageObject(newConnection, this);
                rmo.start();
            }
        }
        catch (IOException e){
            System.out.println("Opening as a leader failed");
        }
        
    }

    public void run_follower(){
        //if follower -> init an go in while true for sending() and reading()
        //Establish connection to leader as a follower
        this.ftl = new FollowerToLeader(this.getLeader(), this);
        this.ftl.run();
        Message message = new Message("MyClient", "MyServer", "payload " + this.ip, MessageType.WRITE);
        String response = this.ftl.sendMessage(message);
        System.out.println(this.ip + " received master response: " + response);

        //Open for possible clients as a follower
        try{
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress(this.ip, this.port);
            serverSocket.bind(address);

            while(true){
                NodeSaver newConnection = new NodeSaver(serverSocket.accept());
                this.initializeStreams(newConnection);
                this.connections.add(newConnection); // -> waiting for first follower to connect before continuing
                ReadMessageObject rmo = new ReadMessageObject(newConnection, this);
                rmo.start();
            }
        }
        catch (IOException e){
            System.out.println(e.toString());
            System.out.println(this.ip + ": connecting to leader failed");
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
            System.out.println("Client send not working by " + this.ip);
            return "failure";
        }
    }   
    
    private Node getLeader(){
        for(Node node : this.listOfNodes){
            if(node.getRole() == Role.LEADER){
                return node;
            }
        }
        System.out.println("Follower Error, no leader found");
        return null;
    }

    private void initializeStreams(NodeSaver nodeSaver){
        Socket socket = nodeSaver.getSocket();
        try{
            InputStream inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ObjectMessageReader omr = new ObjectMessageReader();
            omr.initInputStreams(socket);

            nodeSaver.setDos(dataOutputStream);
            nodeSaver.setOmr(omr);
        }
        catch(IOException e) {
            System.out.println("Node read initialize failed");
        }        
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
    public FollowerToLeader getFtl(){return this.ftl;}
}
