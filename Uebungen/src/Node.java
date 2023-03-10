import java.net.*;
import java.util.LinkedList;
import java.io.*;


public class Node{
    
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
    private int id = -1;
    private Role role = Role.UNKNOWN;
    private LinkedList<Node> listOfNodes = new LinkedList<>();

    public ServerSocket myServer;
    public Socket myMaster;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Node(Role role, String ip, int port){
        this.role = role;
        this.ip = ip;
        this.port = port;
    }

    public void initialize(LinkedList<Node> listOfNodes){
        this.listOfNodes = listOfNodes;
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
        try{
            InputStream inputStream = this.myMaster.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = myMaster.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

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

    private Node getMaster(){
        for(Node node : this.listOfNodes){
            if(node.getRole() == Role.MASTER){
                return node;
            }
        }
        return null;
    }

    public LinkedList<Node> getListOfNodes(){return listOfNodes;}
    public void setListOfNodes(LinkedList<Node> list){this.listOfNodes = list;}
    public String getIp(){return ip;}
    public void setIp(String ip) {this.ip=ip;}
    public int getPort(){return port;}
    public void setPort(int port){this.port=port;}
    public int getId(){return id;}
    public void setId(int id ){this.id=id;}
    public Role getRole(){return role;}
    public void setRole(Role role){this.role = role;}
}
