import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String leader_ip = "127.0.0.1";
        int external_port = 200; //every client can use this for connecting to the net
        int internal_port = 201; //for cumminicating inside of net
    
        Node LeaderNode = new Node(Role.LEADER, leader_ip, external_port);
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        listOfNodes.add(LeaderNode);

        listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.2", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.3", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.4", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.5", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.6", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.7", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.8", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.9", internal_port));
        // listOfNodes.add(new Node(Role.FOLLOWER, "127.0.0.10", internal_port));

        for (Node node : listOfNodes) {
            node.setListOfNodes(listOfNodes);
        }
        
        for (Node node : listOfNodes) {
            node.start();
        }       

        // Client client = new Client("127.0.0.6", external_port);
        // while(true){
        //     client.sendMessage("abc");
        //     TimeUnit.SECONDS.sleep(1);
        // }
    }
}
