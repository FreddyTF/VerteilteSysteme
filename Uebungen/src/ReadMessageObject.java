import java.io.IOException;

public class ReadMessageObject extends Thread{
    NodeSaver nodeSaver;
    
    public ReadMessageObject(NodeSaver nodeSaver)
    {
        this.nodeSaver = nodeSaver;
    }
    
    public void run()
    {
        while(!this.nodeSaver.getSocket().isClosed()){
            try{            
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                System.out.println("Incoming msg: " + message.getPayload() + " Type: " + message.getType());
                this.nodeSaver.getDos().writeUTF("200");
                //write in singleton
            }
            catch (IOException e){
                System.out.println("Server read not working");
            }
        }
        
    }
}