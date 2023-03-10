import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private Object payload;
    private Instant time = Instant.now();
    private String type; // may be an enum too
    private int sequenceNo = -1;
    
    public Message(String sender, String receiver, Object payload, String type)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
        this.type = type;
    }
    
    public Object getPayload () 
    {return this.payload;}

    public void setPayload (Object payload) 
    {this.payload = payload;}

    public Instant getTime () 
    {return this.time;}

    public void setTime (Instant message) 
    {this.time = time;}

    public String getType () 
    {return this.type;}

    public void setType (String type) 
    {this.type = type;}

    public String getSender() 
    {return sender;}

    public void setSender(String sender) 
    {this.sender = sender;}

    public String getReceiver () 
    {return this.receiver;}

    public void setReceiver(String receiver) 
    {this.receiver = receiver;}

    public int getSequenceNo () 
    {return this.sequenceNo;}

    public void setRSequenceNo(int sequenceNo) 
    {this.sequenceNo = sequenceNo;}
}
