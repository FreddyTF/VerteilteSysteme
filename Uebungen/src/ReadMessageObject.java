import java.io.IOException;

public class ReadMessageObject extends Thread {
    NodeSaver nodeSaver;
    Node parentNode;

    public ReadMessageObject(NodeSaver nodeSaver, Node node) {
        this.nodeSaver = nodeSaver;
        this.parentNode = node;
    }

    public void run() {
        if (this.parentNode.getRole() == Role.LEADER) {
            while (!this.nodeSaver.getSocket().isClosed()) {
                try {
                    Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                    System.out.println("Incoming msg for " + this.parentNode.getIp() + ": " + message.getPayload() + " Type: " + message.getType());
                    if(message.getType() == MessageType.WRITE){
                        this.parentNode.saveMessage(message);
                        this.nodeSaver.getDos().writeUTF("200");
                    }
                    else if(message.getType() == MessageType.READ){
                        try {
                            int number = Integer.parseInt(message.getPayload().toString());
                            String answer = this.parentNode.getSavedMessages(number);
                            System.out.println(answer);
                            this.nodeSaver.getDos().writeUTF(answer);
                        } catch (Exception e) {
                            this.nodeSaver.getDos().writeUTF("Please provide a valid request: can't parse number of messages. Set payload to number only.");
                        }
                    }
                    else if(message.getType() == MessageType.UNKNOWN){
                        this.nodeSaver.getDos().writeUTF("201");
                    }                    
                } catch (IOException e) {
                    System.out.println("Server read not working");
                }
            }
        } else if (this.parentNode.getRole() == Role.FOLLOWER) {
            while (!this.nodeSaver.getSocket().isClosed()) {
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                try{
                    if(!this.parentNode.leaderSocket.isClosed()){
                        //TODO: error handling
                        String response = this.parentNode.getCtss().sendMessage(message);
                        this.nodeSaver.getDos().writeUTF(response);
                    }
                }
                catch (IOException e){
                    System.out.println("Problem with communicating between master and client");
                }
            }
        }
    }
}