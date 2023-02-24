public class MainClient {
    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.initialise("localhost", 200);
        myClient.run();
    }
    
}
