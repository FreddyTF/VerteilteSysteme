import java.io.IOException;

public class ReadMessageObject extends Thread{
    NodeSaver nodeSaver;
    
    public ReadMessageObject(NodeSaver nodeSaver)
    {
        this.nodeSaver = nodeSaver;
    }
    
    public void start()
    {
        while(!this.nodeSaver.getSocket().isClosed()){
            try{            
                Message message = this.nodeSaver.getOmr().read(this.nodeSaver.getSocket());
                System.out.println("Incoming msg: " + message.getPayload());
                this.nodeSaver.getDos().writeUTF("200");
                //write in singleton
            }
            catch (IOException e){
                System.out.println("Server read not working");
            }
        }
        
    }
}