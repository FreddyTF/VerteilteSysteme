import java.net.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.io.*;



public class Node extends Thread{
    
    // this max client definition is not available in "old "
    // implementations
    /**
    * this method initializes the server
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
    private LinkedList<MessageSaver> messageSaverStorage = new LinkedList<>();

    public Socket leaderSocket;   //the one leader, if self is a leader -> empty
    public LinkedList<ConnectionSaver> connections = new LinkedList<ConnectionSaver>(); //all connected nodes
    public LinkedList<NodeSaver> allNodes = new LinkedList<NodeSaver>(); //all nodes, incl. own -> same list for every node
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;
    private ConnectToServerSocket ctss;

    public Node(Role role, String ip, int port, Node leader){
        this.role = role;
        this.ip = ip;
        this.port = port;
        if(leader == null){
            this.allNodes.add(new NodeSaver(ip, port, role));
        }
        else{
            this.allNodes.add(new NodeSaver(leader.getIp(), leader.getPort(), leader.getRole()));
        }
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
                ConnectionSaver newConnection = new ConnectionSaver(serverSocket.accept());
                this.sendUpdatedNodeList();
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
        this.ctss = new ConnectToServerSocket(this.getLeaderNode(), this.ip, this);
        this.ctss.run();
        NodeSaver thisAsNodeSaver = new NodeSaver(this.ip, this.port, this.role);
        Message message = new Message(this.ip, this.getLeaderNode().getIp(), this.ip, MessageType.INITIALIZE, thisAsNodeSaver);
        String response = this.ctss.sendMessage(message);
        System.out.println(this.ip + " received master response: " + response);

        //Open for possible clients as a follower
        try{
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress(this.ip, this.port);
            serverSocket.bind(address);

            while(true){
                ConnectionSaver newConnection = new ConnectionSaver(serverSocket.accept());
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

    private void sendUpdatedNodeList(){
        Message message = new Message("Client", "receiver", this.allNodes, MessageType.UPDATE_NODE_LIST);
        
    }
    
    private NodeSaver getLeaderNode(){
        for(NodeSaver nodeSaver: this.allNodes){
            if(nodeSaver.getRole() == Role.LEADER){
                return nodeSaver;
            }
        }
        System.out.println("Follower Error, no leader found");
        return null;
    }

    private void initializeStreams(ConnectionSaver nodeSaver){
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

    public LocalDateTime saveMessage(Message message){
        try {
            if(this.messageSaverStorage.size() > 9){
                this.messageSaverStorage.removeFirst();            
            }
            LocalDateTime savingTime = LocalDateTime.now();
            this.messageSaverStorage.add(new MessageSaver(message, savingTime));
            return savingTime;
        } catch (Exception e) {
            System.out.println("Saving message was not possible!");
            return null;
        }
    }

    public String getSavedMessages(int count){
        if(count > this.messageSaverStorage.size()){
            String toReturn = "";
            for (MessageSaver messageSaver : this.messageSaverStorage) {
                toReturn += messageSaver.getMessage().getPayload();
                if(messageSaver != this.messageSaverStorage.getLast()){
                    toReturn += ", ";
                }
            }
            return toReturn;
        }
        else {
            String toReturn = "";
            for (int i = this.messageSaverStorage.size() - count; i < this.messageSaverStorage.size(); i++) { 
                MessageSaver messageSaver = this.messageSaverStorage.get(i);
                toReturn += messageSaver.getMessage().getPayload();
                if(messageSaver != this.messageSaverStorage.getLast()){
                    toReturn += ", ";
                }
            }
            return toReturn;
        }
    }

    public String getIp(){return this.ip;}
    public void setIp(String ip) {this.ip=ip;}
    public int getPort(){return this.port;}
    public void setPort(int port){this.port=port;}
    public int getNodeId(){return this.nodeId;}
    public void setNodeId(int id ){this.nodeId=id;}
    public Role getRole(){return this.role;}
    public void setRole(Role role){this.role = role;}
    public ConnectToServerSocket getCtss(){return this.ctss;}
    public LinkedList<NodeSaver> getAllNodes(){return this.allNodes;}
    public void setNodeList(LinkedList<NodeSaver> newList){this.allNodes = newList;}


}
