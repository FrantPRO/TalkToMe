/*
 * Talk To Me
 * Main class
 */
package talktome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Frantsuzov S.
 */
public class TalkToMe extends JFrame implements InterfaceTTM {

    /**
     * Variable LogFile for object of class LogFile
     */
    final LogFile LOG;

    /**
     * Variable ReadFile for object of class ReadFile
     */
    final ReadFile READ;

    /**
     * Variable Linguistic for object of class Linguistic
     */
    final Linguistic LING;

    /**
     * Variable String for name of file
     */
    private String filename = "answers.txt";

    /**
     * Variable String for phrases
     */
    private String question;

    /**
     * Variable ArrayList for collection of phrases
     */
    private ArrayList<String> answers;

    /**
     * Variable BufferedReader for input stream
     */
    private BufferedReader input;

    /**
     * Variable GUImode for object of class GUImode
     */
    private GUImode guiMode;

    /**
     * Constructor without parameters
     */
    public TalkToMe() {
        System.out.println("Default constr.");
        this.READ = new ReadFile();
        this.LOG = new LogFile();
        this.LING = new Linguistic(this);
    }

    /**
     * Constructor with parameter String for console mode
     *
     * @param txt String
     */
    public TalkToMe(String txt) {
        this();
        System.out.println("Console mode");
        input = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Constructor with String[] parameter for GUI mode
     *
     * @param args String
     */
    public TalkToMe(String[] args) {
        this();
        System.out.println("GUI mode");
        guiMode = new GUImode(this);
    }

    /**
     * Constructor with parameters for run GUI mode from concole
     *
     * @param read ReadFile link
     * @param log LogFile link
     * @param ling Linguistic link
     */
    public TalkToMe(ReadFile read, LogFile log, Linguistic ling) {
        System.out.println("GUI mode from console");
        this.READ = read;
        this.LOG = log;
        this.LING = ling;
        guiMode = new GUImode(this);
    }

    /**
     * Main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TalkToMe ttm;
        if (args.length != 0 && args[0].equalsIgnoreCase("g")) {
            ttm = new TalkToMe(args);
            try {
                ttm.readFile(ttm.filename);
            } catch (Exception ex) {
                ttm.guiMode.setMsgToChat(ERROR_READ_FILE + ex);
                ttm.logWriteComp(ERROR_READ_FILE + ex);
            }
            ttm.guiMode.setMsgToChat(ttm.sayHello());
            ttm.logWriteComp(ttm.sayHello());

        } else {
            ttm = new TalkToMe("Console");
            try {
                ttm.readFile(ttm.filename);
            } catch (Exception ex) {
                System.err.println(ERROR_READ_FILE + ex);
                ttm.logWriteComp(ERROR_READ_FILE + ex);
            }
            ttm.answer(ttm.sayHello());
            ttm.talk();
            ttm.disconnect();
        }
    }

    /**
     * Method for talk with user
     */
    private void talk() {
        while (true) {
            question();
            logWriteUser(question);
            if (exit()) {
                break;
            } else if (stop()) {
                answer(MSG_FOR_STOP);
                while (true) {
                    question();
                    if (start()) {
                        logWriteUser(question);
                        answer(MSG_FOR_START);
                        break;
                    } else if (exit()) {
                        logWriteUser(question);
                        break;
                    }
                }
                continue;
            } else if (useAnotherFile()) {
                setNewFile();
                try {
                    readFile(filename);
                } catch (Exception e) {
                    System.err.println(ERROR_READ_FILE + e);
                    logWriteComp(ERROR_READ_FILE + e);
                    continue;
                }
                answer(NEW_FILE + filename);
                continue;
            } else if (setGUImode()) {
                System.out.println("exit");
                this.dispose();
                runGuiMode();
                return;
            }
            answer(getAnswer());
        }
        answer(sayGoodBye());
    }

    /**
     * Method for run GUI mode
     */
    private void runGuiMode() {
        TalkToMe ttmG = new TalkToMe(this.READ, this.LOG, this.LING);
        try {
            ttmG.readFile(ttmG.filename);
        } catch (Exception ex) {
            ttmG.guiMode.setMsgToChat(ERROR_READ_FILE + ex);
            ttmG.logWriteComp(ERROR_READ_FILE + ex);
        }
        ttmG.guiMode.setMsgToChat(ttmG.sayHello());
        ttmG.logWriteComp(ttmG.sayHello());
    }

    /**
     * Method for read file and add it to the collection
     *
     * @param filename String
     * @throws java.lang.Exception
     */
    public void readFile(String filename) throws Exception {
        answers = (ArrayList) READ.getList(filename);
    }

    /**
     * Method return collection of phrases
     *
     * @return ArrayList
     */
    public ArrayList getAnswers() {
        return answers;
    }

    /**
     * Method for sending message to the console and record it in the log
     *
     * @param msg String
     */
    private void answer(String msg) {
        System.out.println("Comp: " + msg);
        logWriteComp(msg);
    }

    /**
     * Method for record in the log
     *
     * @param msg String
     */
    public void logWriteUser(String msg) {
        LOG.write("User: " + msg);
    }

    /**
     * Method for record in the log
     *
     * @param msg String
     */
    public void logWriteComp(String msg) {
        LOG.write("Comp: " + msg);
    }

    /**
     * Method for output first phrase from collection
     *
     * @return String
     */
    private String sayHello() {
        return answers.get(0);
    }

    /**
     * Method for output last phrase from collection
     *
     * @return String
     */
    public String sayGoodBye() {
        return answers.get(answers.size() - 1);

    }

    /**
     * Method to read line from the console and copy it to the Variable
     */
    public void question() {
        System.out.print("User: ");
        try {
            question = input.readLine();
        } catch (IOException ex) {
            System.err.println("Error reading the input stream. " + ex);
            logWriteComp("Error reading the input stream. " + ex);
        }
    }

    /**
     * Method assigns the input value of the frame, the records in the log,
     * generating a new phrase, sending a new phrase in the frame and write it
     * to a log.
     *
     * @param msg String
     */
    public void questionG(String msg) {
        question = msg;
        logWriteUser(msg);
        String txt = getAnswer();
        guiMode.setMsgToChat(txt);
        logWriteComp(txt);
    }

    /**
     * Method returns true if the sentence matches the pattern and false
     * otherwise
     *
     * @return boolean
     */
    private boolean exit() {
        return question.equalsIgnoreCase(EXIT);
    }

    /**
     * Method returns true if the sentence matches the pattern and false
     * otherwise
     *
     * @return boolean
     */
    private boolean stop() {
        return question.equalsIgnoreCase(STOP);
    }

    /**
     * Method returns true if the sentence matches the pattern and false
     * otherwise
     *
     * @return boolean
     */
    private boolean start() {
        return question.equalsIgnoreCase(START);
    }

    /**
     * Method returns true if the sentence matches the pattern and false
     * otherwise
     *
     * @return boolean
     */
    private boolean useAnotherFile() {
        return question.contains(CHANGEFILE);
    }

    /**
     * Method sets a new file with a list of phrases
     */
    private void setNewFile() {
        filename = question.replaceAll(CHANGEFILE, "").replace("\"", "").trim();
    }

    /**
     * Method returns true if the sentence matches the pattern and false
     * otherwise
     *
     * @return boolean
     */
    private boolean setGUImode() {
        return question.equalsIgnoreCase(GUI);
    }

    /**
     * Method returns a randomly selected phrase from the collection
     *
     * @return String
     */
    private String getAnswer() {
        return LING.getAnswer(question);
    }

    /**
     * Method tries to close the input stream
     */
    private void disconnect() {
        try {
            input.close();
        } catch (IOException ex) {
            System.err.println("Error closing the input stream. " + ex);
            logWriteComp("Error closing the input stream. " + ex);
        }
    }
}
