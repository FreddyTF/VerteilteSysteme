import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        Server myServer = new Server();
        Client myClient = new Client();
        myServer.initialise("localhost", 200);
        myClient.initialise("localhost", 200);
        
        Thread myServerThread = new Thread(myServer);
        Thread myClientThread = new Thread(myClient);
        myServerThread.start();
        myClientThread.start();
    }

    public void taskTwo(){
        // check if the file does not exist

        String fileName = "my-data.txt";

        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        customFileWriter writer1 = new customFileWriter(fileName);
        customFileWriter writer2 = new customFileWriter(fileName);
        Thread runner = new Thread(writer1);
        Thread runner2 = new Thread(writer2);
        writer1.setTID(String.valueOf(runner.getId()));
        writer2.setTID(String.valueOf(runner2.getId()));

        runner.start();
        runner2.start();
    }

}