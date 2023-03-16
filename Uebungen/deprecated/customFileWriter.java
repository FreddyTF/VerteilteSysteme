import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Random;
import java.time.Instant;

public class customFileWriter implements Runnable{
    String fileNamePublic;
    String id;
    String processID;
    public customFileWriter(String fileName){
        this.id = String.valueOf(Thread.currentThread().getNodeId());
        this.processID = String.valueOf(ProcessHandle.current().pid());
        this.fileNamePublic = fileName;
    }

    public void run(){
        while(true)
        {
            // readAndPrintFile();
            String text = "tID " + this.id + " pID: " + this.processID + ":";
            Integer rand = new Random().nextInt(250);
            try{
                Thread.sleep(250);
            }
            catch (InterruptedException e){
                System.out.println("Interrupted");
            }
            if(rand % 2 == 0){
                //write input
                text = text.concat(" Test Text ");
                Instant strDate = Instant.now();
                text = text.concat(" " + strDate);
                text = text.concat(System.lineSeparator());
                fileAppender(text);
            }
            else{
                readAndPrintFile();
            }

            
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

    public void setTID(String tID){
        this.id = tID;
    }
}
