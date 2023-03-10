import java.util.LinkedList;
import java.util.List;

public class MainServer {
    public static void main(String[] args) {
        Node MasterNode = new Node(Role.MASTER, "localhost", 200);
        Node SlaveNode = new Node(Role.SLAVE, "localhost", 200);
        Node SlaveNode2 = new Node(Role.SLAVE, "localhost", 200);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();

        listOfNodes.add(MasterNode);
        listOfNodes.add(SlaveNode);
        listOfNodes.add(SlaveNode2);

        MasterNode.initialize(listOfNodes);
        SlaveNode.initialize(listOfNodes);
        SlaveNode2.initialize(listOfNodes);

        for (Node node : listOfNodes) {
            node.setListOfNodes(listOfNodes);
        }

        MasterNode.readMessage();
    }
}
