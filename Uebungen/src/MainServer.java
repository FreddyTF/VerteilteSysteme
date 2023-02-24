public class MainServer {
    public static void main(String[] args) {
        Server myServer = new Server();
        
        myServer.initialise("localhost", 200);
       
        System.out.println("Main will call server");
        myServer.run();
    }
}
