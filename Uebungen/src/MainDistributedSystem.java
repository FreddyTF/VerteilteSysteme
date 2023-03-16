import java.util.LinkedList;

public class MainDistributedSystem {
    public static void main(String[] args) {
        String leader_ip = "127.0.0.1";
        int leader_port = 200;
    
        Node LeaderNode = new Node(Role.LEADER, leader_ip, leader_port);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        listOfNodes.add(LeaderNode);

        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.2", leader_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.3", leader_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.4", leader_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.5", leader_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.6", leader_port));

        for (Node node : listOfNodes) {
            node.setListOfNodes(listOfNodes);
        }

        for (Node node : listOfNodes) {
            node.start();
        }       
    }
}