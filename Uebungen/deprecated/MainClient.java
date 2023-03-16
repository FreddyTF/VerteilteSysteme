
public class MainClient {
    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.initialize("127.0.0.1", 200);
        int i = 0;
        while(true){
            Message message = new Message("MyClient", "MyServer", "payload " + i, "Message");
            myClient.sendMessage(message);
            i++;
        }
    }
}