import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ObjectMessageReader {
    ObjectInputStream ois = null ;
    /**
    * this method is used to initialise the stream to read
    from ;
    * socket and stream are linked together ; if one is
    closed the other is closed too ; only 1 stream is
    needed
    * @param socket socket to link the stream to
    * @return true , if stream is initialised , else false
    */
    public boolean initInputStreams(Socket socket)
    {
        try {
            InputStream is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            return true;
        } catch ( Exception e ) {
            System.err.println(e.toString());
            return false ;
        }
    }
    
    /**
    * this method reads objects from a given socket
    * @param socket socket to read an object from
    * @return the message object or null , in case of an
    error
    */
    public Message read (Socket socket)
    {
        Message ret = null ;
        try {
            ret = (Message) ois.readObject();
        } catch (Exception e){
            System.err.println(e.toString());
        }
        return ret;
    }
}