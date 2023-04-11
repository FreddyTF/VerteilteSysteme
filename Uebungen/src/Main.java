import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String leader_ip = "127.0.0.1";
        int port = 200;
    
        Node LeaderNode = new Node(Role.LEADER, leader_ip, port, null);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        listOfNodes.add(LeaderNode);

        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.2", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.3", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.4", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.5", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.6", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.7", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.8", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.9", port, LeaderNode));
        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.10",port, LeaderNode));
        
        for (Node node : listOfNodes) {
            node.start();
        }       

        TimeUnit.SECONDS.sleep(1);

        Client client1 = new Client(listOfNodes.get(1), "127.0.1.1");
        Client client2 = new Client(listOfNodes.get(0), "127.0.1.2");
        Client client3 = new Client(listOfNodes.get(0), "127.0.1.3");
        Client client4 = new Client(listOfNodes.get(0), "127.0.1.4");
        Client client5 = new Client(listOfNodes.get(0), "127.0.1.5");
        Client client6 = new Client(listOfNodes.get(0), "127.0.1.6");
        
        while(true){
            TimeUnit.SECONDS.sleep(1);
            client1.sendMessage("abc", MessageType.WRITE);
            client1.sendMessage("def", MessageType.WRITE);
            client1.sendMessage("hij", MessageType.WRITE);
            client1.sendMessage("klm", MessageType.WRITE);
            client1.sendMessage("nop", MessageType.WRITE);
            client1.sendMessage("5", MessageType.READ);
            client1.sendMessage("ad", MessageType.READ);
        }
    }
}
