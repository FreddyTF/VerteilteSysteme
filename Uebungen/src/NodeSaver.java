import java.io.DataOutputStream;
import java.net.Socket;

public class NodeSaver {

    public Socket socket;
    public ObjectMessageReader omr;
    public DataOutputStream dos;

    public Socket getSocket() {return this.socket;}
    public void setSocket(Socket socket) {this.socket = socket;}

    public ObjectMessageReader getOmr() {return this.omr;}
    public void setOmr(ObjectMessageReader omr) {this.omr = omr;}

    public DataOutputStream getDos() {return this.dos;}
    public void setDos(DataOutputStream dos) {this.dos = dos;}

    public NodeSaver(Socket socket){
        this.socket = socket;
    }


}
