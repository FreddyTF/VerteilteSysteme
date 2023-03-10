import java.util.LinkedList;
import java.util.List;

public class MainServer {
    public static void main(String[] args) {
        String master_ip = "127.0.0.1";
        int master_port = 200;
    
        Node MasterNode = new Node(Role.MASTER, master_ip, master_port);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        listOfNodes.add(MasterNode);

        listOfNodes.add(new Node(Role.SLAVE, "127.0.0.2", master_port));
        listOfNodes.add(new Node(Role.SLAVE, "127.0.0.3", master_port));
        listOfNodes.add(new Node(Role.SLAVE, "127.0.0.4", master_port));
        listOfNodes.add(new Node(Role.SLAVE, "127.0.0.5", master_port));
        listOfNodes.add(new Node(Role.SLAVE, "127.0.0.6", master_port));

        for (Node node : listOfNodes) {
            node.setListOfNodes(listOfNodes);
        }

        for (Node node : listOfNodes) {
            node.start();
        }       
        
        MasterNode.readMessage();
    }
}
