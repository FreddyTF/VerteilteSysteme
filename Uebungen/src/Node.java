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

    public Socket myMaster;
    public Socket myClient;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;

    public Node(Role role, String ip, int port){
        this.role = role;
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        //if slave -> init an go in while true for sending() and reading()
        if(this.role == Role.SLAVE){
            try{
                Node master = this.getMaster();
                this.myMaster = new Socket(master.getIp(), master.getPort());
                OutputStream outputStream = this.myMaster.getOutputStream();
                this.objectOutputStream = new ObjectOutputStream(outputStream);
                InputStream inputStream = this.myMaster.getInputStream();
                this.dataInputStream = new DataInputStream(inputStream);
                System.out.println("Connected successfully to Master: " + master.getIp());
            }
            catch (IOException e){
                System.out.println("Slave: connecting to Master failed");
            }

            Message message = new Message("MyClient", "MyServer", "payload " + 1, "Message");
            while(true){
                this.sendMessage(message);
            }
        }
        else if (this.role == Role.MASTER){
            //if master -> init and go in while true for read_message() and send_message()
            try{
                ServerSocket serverSocket = new ServerSocket(this.port);
                System.out.println("Master accepting incoming messages from now on");
                this.myClient = serverSocket.accept();
            }
            catch (IOException e){
                System.out.println("Master: Opening as a Master failed");
            }
            System.out.println("Reading messages from now on");
            this.readMessages(this.myClient);
        }
    }

    public void closeSockets(){
        try {
            this.myMaster.close();
        } catch (Exception e) { 
            System.out.println("An error occured whilst closing the sockets.");
        }
    }

    public void readMessages(Socket myClient){
        try{
            InputStream inputStream = myClient.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = myClient.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ObjectMessageReader omr = new ObjectMessageReader();
            omr.initInputStreams(myClient);
            
            while (!myClient.isClosed()){
                Message message = omr.read(myClient);
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
    
    private Node getMaster(){
        for(Node node : this.listOfNodes){
            if(node.getRole() == Role.MASTER){
                return node;
            }
        }
        System.out.println("Fehler hier, kein Master in sicht");
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
