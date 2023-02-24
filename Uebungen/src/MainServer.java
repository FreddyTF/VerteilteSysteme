public class MainServer {
    public static void main(String[] args) {
        Server myServer = new Server();
        myServer.initialize("localhost", 200);
        myServer.readMessage();
    }
}
