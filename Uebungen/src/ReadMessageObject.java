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
                    this.nodeSaver.getDos().writeUTF("200");
                    // write in singleton
                } catch (IOException e) {
                    System.out.println("Server read not working");
                }
            }
        } else if (this.parentNode.getRole() == Role.FOLLOWER) {
            while (!this.nodeSaver.getSocket().isClosed()) {
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                try{
                    if(!this.parentNode.leaderSocket.isClosed()){
                        String response = this.parentNode.getCtss().sendMessage(message);
                        this.nodeSaver.getDos().writeUTF("200");
                    }
                }
                catch (IOException e){
                    System.out.println("Problem with communicating between master and client");
                }
            }
        }
    }
}