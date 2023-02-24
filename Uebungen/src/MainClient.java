import java.time.Instant;

public class MainClient {
    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.initialize("localhost", 200);
        int i = 0;
        while(true){
            myClient.sendMessage("Message Nr." + i + "; Time: " + Instant.now());
            i++;
        }
    }
}