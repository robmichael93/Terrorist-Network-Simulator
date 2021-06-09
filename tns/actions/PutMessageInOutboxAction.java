package tns.actions;
import tns.frames.*;
import tns.messages.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.lang.reflect.*;

/**
 * This action creates a new message using Java's reflection capabilities and 
 * then places the object in the agent's outbox.
 * @author  Rob Michael and Zac Staples
 */
public class PutMessageInOutboxAction implements Frame {
    
    /**
     * The agent who owns the outbox.
     */
    private Agent agent;
    /**
     * The class name of the Message to create.
     */
    private String messageType;
    /**
     * The argument Class objects for creating the Message.
     */
    private Class[] argumentTypes;
    /**
     * The arguments for the Message's constructor.
     */
    private Object[] arguments;
    
    /** 
     * Creates a new instance of PutMessageInOutboxAction 
     * @param agent The agent who owns the outbox.
     * @param messageType The class name of the Message to create.
     * @param argumentTypes The argument Class objects for creating the Message.
     * @param arguments The arguments for the Message's constructor.
     */
    public PutMessageInOutboxAction(Agent agent, String messageType, Class[] argumentTypes, Object[] arguments) {
        this.agent = agent;
        this.messageType = messageType;
        this.argumentTypes = argumentTypes;
        this.arguments = arguments;
    } // end PutMessageInOutboxAction constructor
    
    /**
     * The execute method creates a new Message object using reflection and then
     * puts in the agent's outbox.
     */
    public void execute() {
        Message message = createMessage(messageType, argumentTypes, arguments);
        ((TerroristAgent)agent).getOutbox().addMessage(message);
    } // end execute
    
    /**
     * Creates the Message that corresponds to the passed in class name and 
     * associated arguments.
     *
     * @param className The className of the Message to be created.
     * @param argumentTypes The argument Class objects for the Message's 
     * constructor.
     * @param arguments The arguments for the Message's constructor.
     * @return Message The created Message.
     *
     */
    private Message createMessage( String className, Class[] argumentTypes, Object[] arguments) {
//        System.out.println("Attempting to create class " + className);
        Class cl = null;
        try {
           cl = Class.forName( "tns.messages." + className );
        } catch ( ClassNotFoundException e ) {
           System.err.println( "Can't find your darned class." );
           System.exit( 0 );
        } // end try-catch

        Constructor c = null;
        Object o = null;
        try {
            c = cl.getConstructor(argumentTypes);
            o = c.newInstance(arguments);
        } catch (NoSuchMethodException e) {
            System.err.println("Can't find you class' constructor.");
            System.exit(0);
        } catch (SecurityException e) {
            System.err.println("Security Exception. Can't access your class.");
            System.exit(0);
        } catch ( InstantiationException e ) {
           System.err.println( "Can't make your darned class." );
           System.exit( 0 );
        } catch ( IllegalAccessException e ) {
           System.err.println( "Can't access your darned class." );
           System.exit( 0 );
        } catch (IllegalArgumentException e) {
            System.err.println("Can't create your class.  Illegal arguments.");
            System.exit(0);
        } catch (InvocationTargetException e) {
            System.err.println("Can't create your class.  Contructor threw an exception.");
            System.exit(0);
        } // end try-catch

        return (Message)o;

    }// end createMessage
} // end class PutMessageInOutboxAction
