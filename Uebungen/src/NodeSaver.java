import java.io.DataOutputStream;
import java.net.Socket;

public class NodeSaver {

    private Socket socket;
    private ObjectMessageReader omr;
    private DataOutputStream dos;
    private Node node;

    public Socket getSocket() {return this.socket;}
    public void setSocket(Socket socket) {this.socket = socket;}

    public ObjectMessageReader getOmr() {return this.omr;}
    public void setOmr(ObjectMessageReader omr) {this.omr = omr;}

    public DataOutputStream getDos() {return this.dos;}
    public void setDos(DataOutputStream dos) {this.dos = dos;}

    public Node getNode() {return this.node;}
    public void setNode(Node node) {this.node = node;}

    public NodeSaver(Socket socket){
        this.socket = socket;
    }


}
