import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.time.Instant;


public class Main {
    public static void main(String[] args) {
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
        runner.start();
        runner2.start();
    }


}