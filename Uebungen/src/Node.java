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

    public ServerSocket myServer;
    public Socket myMaster;
    public Socket myClient;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Node(Role role, String ip, int port){
        this.role = role;
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        //TODO: implement connection between node and client
        if(this.role == Role.SLAVE){
            try{
                Node master = this.getMaster();
                this.myMaster = new Socket(master.getIp(), master.getPort());
                OutputStream outputStream = this.myMaster.getOutputStream();
                this.dataOutputStream = new DataOutputStream(outputStream);
                InputStream inputStream = this.myMaster.getInputStream();
                this.dataInputStream = new DataInputStream(inputStream);
                System.out.println("Connected successfully to Master: " + master.getIp());
            }
            catch (IOException e){
                System.out.println("Slave: connecting to Master failed");
            }
        }
        else if (this.role == Role.MASTER){
            try{
                ServerSocket serverSocket = new ServerSocket(this.port);
                myMaster = serverSocket.accept();
            }
            catch (IOException e){
                System.out.println("Master: Opening as a Master failed");
            }
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
        if(this.role == Role.MASTER){
            try{
                while (!myMaster.isClosed()){
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
