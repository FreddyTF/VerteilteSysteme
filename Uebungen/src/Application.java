/**
 * This class is used as an example for Threading
 */
package eu.boxwork.dhbw.examples ;
public class MyRunnableClass implements Runnable {
    /**
     3
     * the run method will be called by the thread
     * when we use the start () -Method
     */
    public void run ()
    {
        System . out . println ("Am I a threaded method ?") ;
    }
}