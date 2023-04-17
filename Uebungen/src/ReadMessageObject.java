import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class ReadMessageObject extends Thread {
    ConnectionSaver nodeSaver;
    Node parentNode;

    public ReadMessageObject(ConnectionSaver nodeSaver, Node node) {
        this.nodeSaver = nodeSaver;
        this.parentNode = node;
    }

    public void run() {
        if (this.parentNode.getRole() == Role.LEADER) {
            this.run_leader();
        } else if (this.parentNode.getRole() == Role.FOLLOWER) {
           this.run_follower();
        }
    }

    private void run_leader(){
        while (!this.nodeSaver.getSocket().isClosed()) {
            try {
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                System.out.println("Incoming msg for " + this.parentNode.getIp() + ": " + message.getPayload() + " Type: " + message.getType());
                if(message.getType() == MessageType.WRITE){
                    LocalDateTime answer = this.parentNode.saveMessage(message);
                    if(answer != null){
                        this.nodeSaver.getDos().writeUTF("200: " + answer.toString());
                    }
                    else{
                        this.nodeSaver.getDos().writeUTF("400: failed to save");
                    }
                }
                else if(message.getType() == MessageType.READ){
                    try {
                        int number = Integer.parseInt(message.getPayload().toString());
                        String answer = this.parentNode.getSavedMessages(number);
                        this.nodeSaver.getDos().writeUTF(answer);
                    } catch (Exception e) {
                        this.nodeSaver.getDos().writeUTF("Please provide a valid request: can't parse number of messages. Set payload to number only.");
                    }
                }
                else if(message.getType() == MessageType.INITIALIZE){
                    this.parentNode.allNodes.add(message.getNodeSaver());
                    this.nodeSaver.getDos().writeUTF("202: inited new follower");
                }
                else if(message.getType() == MessageType.UNKNOWN){
                    this.nodeSaver.getDos().writeUTF("201");
                }                    
            } catch (IOException e) {
                System.out.println("Server read not working");
            }
        }
    }

    private void run_follower(){
        while (!this.nodeSaver.getSocket().isClosed()) {
            try{
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                if (message.getType() == MessageType.READ || message.getType() == MessageType.WRITE){
                    if(!this.parentNode.leaderSocket.isClosed()){
                        //TODO: error handling
                        String response = this.parentNode.getCtss().sendMessage(message);
                        this.nodeSaver.getDos().writeUTF(response);
                    }
                }
                else if(message.getType() == MessageType.UPDATE_NODE_LIST){
                    System.out.println("received upddated node list");
                    LinkedList<NodeSaver> test = ((LinkedList<NodeSaver>)message.getPayload());
                    this.parentNode.setNodeList(test);    
                    this.nodeSaver.getDos().writeUTF("Piss dich");
                }
                else{
                    System.out.println("Fuck You");
                }
            
            }
            catch (IOException e){
                System.out.println("Problem with communicating between master and client");
            }            
        }
    }
    
}