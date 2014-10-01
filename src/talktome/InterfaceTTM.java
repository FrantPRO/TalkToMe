/*
 * Talk To Me
 * Interface for Talk To Me
 */
package talktome;

/**
 *
 * @author Frantsuzov S.
 */
public interface InterfaceTTM {

    /**
     * Message for stop the program
     */
    String STOP = "stop";
//    String STOP = "\"stop talking\"";
    
    /**
     * Message for start the program
     */
    String START = "start";
//    String START = "\"start talking\"";
    
    /**
     * Message for exit the program
     */
    String EXIT = "exit";
//    String EXIT = "\"goodbye\"";
    
    /**
     * Message for change file
     */
    String CHANGEFILE = "file: ";
//    String CHANGEFILE = "\"use another file: \"";
    
    /**
     * Message for use GUI mode
     */
    String GUI = "gui";
//    String GUI = "\"use graphics\"";
    
    /**
     * Message when the program is stopped
     */
    String MSG_FOR_STOP = "The program was stopped!";
    
    /**
     * Message when the program is started
     */
    String MSG_FOR_START = "Program start...";
    
    String NEW_FILE = "File changed. New file: ";
    
    String ERROR_READ_FILE = "Error read file or file not exist. ";
}
