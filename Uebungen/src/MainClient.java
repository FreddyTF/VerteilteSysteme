public class MainClient {
    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.initialize("localhost", 200);
        while(true){
            myClient.sendMessage("Hallo Test 123");
        }
    }
}