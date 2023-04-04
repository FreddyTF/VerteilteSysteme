import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String leader_ip = "127.0.0.1";
        int port = 200;
    
        Node LeaderNode = new Node(Role.LEADER, leader_ip, port);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        listOfNodes.add(LeaderNode);

        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.2", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.3", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.4", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.5", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.6", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.7", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.8", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.9", port));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.10",port));

        for (Node node : listOfNodes) {
            node.setListOfNodes(listOfNodes);
        }
        
        for (Node node : listOfNodes) {
            node.start();
        }       

        Client client = new Client("127.0.0.6", port);
        while(true){
            client.sendMessage("abc");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
