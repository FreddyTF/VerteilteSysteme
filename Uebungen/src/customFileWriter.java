import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Calendar;
import java.time.Instant;

public class customFileWriter {
    String fileNamePublic;
    Integer id;
    public customFileWriter(String fileName){
        this.id = 1;
        this.fileNamePublic = fileName;
    }

    public void run(){
        while(true)
        {
            // readAndPrintFile();
            String text = "Test text";
            //readInput();
            fileAppender(text);
        }
    }

    private void fileAppender(String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileNamePublic, true));
            writer.write(text);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
    private String readInput() {
        // read the input by the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter some text: ");

        String text = "Process " + this.id + ":";

        text = text.concat(scanner.nextLine());

        String strDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        text = text.concat(" ");
        text = text.concat(strDate);
        text = text.concat(System.lineSeparator());
        return text;
    }

    private void readAndPrintFile() {
        System.out.println("The file is read.");
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileNamePublic))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}
