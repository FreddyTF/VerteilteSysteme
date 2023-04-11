import java.time.LocalDateTime;

public class MessageSaver{
    private Message message;
    private LocalDateTime timeStamp;

    
    public MessageSaver(Message message, LocalDateTime timestamp) {
        this.message = message;
        this.timeStamp = timestamp;
    }
     
    public Message getMessage() {return this.message;}
    public void setMessage(Message message) {this.message = message;}
    public LocalDateTime getTimeStamp() {return this.timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp) {this.timeStamp = timeStamp;}
}
