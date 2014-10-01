/*
 * Talk To Me
 * Class Graphic User Interface
 */
package talktome;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Frantsuzov S.
 */
public class GUImode extends JFrame implements InterfaceTTM {

    /**
     * Button for sending message to chat
     */
    private final JButton button = new JButton("Send");

    /**
     * Field to enter a message
     */
    private final JTextField input = new JTextField();

    /**
     * Area to display chat messages
     */
    private final JTextArea chat = new JTextArea();

    /**
     * Variable for the object of listener class
     */
    private final GUImodeListener listener = new GUImodeListener();

    /**
     * Variable for the object of class TalkToMe
     */
    private final TalkToMe main;

    /**
     * The class constructor. Creates a graphical user interface
     *
     * @param main Reference to the object of class TalkToMe
     */
    public GUImode(TalkToMe main) {
        super("Talk To Me");
        this.main = main;
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(listener);
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(5, 5, 0, 5);
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        chat.setEditable(false);
        chat.setLineWrap(rootPaneCheckingEnabled);
        chat.setWrapStyleWord(rootPaneCheckingEnabled);
        JScrollPane scroll = new JScrollPane(chat);
        gbl.setConstraints(scroll, c);
        add(scroll);
        
        c.insets = new Insets(5, 5, 5, 5);
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridy = GridBagConstraints.WEST;
        c.weightx = 0.9;
        c.weighty = 0.0;
        
        gbl.setConstraints(input, c);
        add(input);
        c.weightx = 0.1;
        gbl.setConstraints(button, c);
        add(button);
        
        button.setActionCommand("1");
        button.addActionListener(listener);
        JMenuItem start = new JMenuItem("Start");
        start.setActionCommand("2");
        start.addActionListener(listener);
        JMenuItem stop = new JMenuItem("Stop");
        stop.setActionCommand("3");
        stop.addActionListener(listener);
        JMenuItem openFile = new JMenuItem("Change file...");
        openFile.setActionCommand("4");
        openFile.addActionListener(listener);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setActionCommand("5");
        exit.addActionListener(listener);
        JMenuItem about = new JMenuItem("About");
        about.setActionCommand("6");
        about.addActionListener(listener);
        JMenu menu = new JMenu("Menu");
        menu.add(start);
        menu.add(stop);
        menu.add(openFile);
        menu.add(exit);
        JMenu help = new JMenu("Help");
        help.add(about);
        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        bar.add(help);
        setJMenuBar(bar);
        setVisible(true);
    }

    /**
     * Nested class event
     */
    class GUImodeListener extends WindowAdapter implements ActionListener {

        @Override
        public void windowClosing(WindowEvent e) {
            if (main == null) {
                Object src = e.getSource();
                if (src instanceof Frame) {
                    ((Frame) src).dispose();
                }
            } else {
                setMsgToChat(main.sayGoodBye());
                close();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd.charAt(0)) {
                case '1':       // Send button
                    main.questionG(getInput());
                    chat.setCaretPosition(chat.getDocument().getLength());
                    input.grabFocus();
                    break;
                case '2':       // Start
                    start();
                    break;
                case '3':       // Stop
                    stop();
                    break;
                case '4':       // Change file
                    openFile();
                    break;
                case '5':       // Exit
                    setMsgToChat(main.sayGoodBye());
                    close();
                    break;
                case '6':       // About
                    aboutDialog();
                    break;
            }
        }
    }

    /**
     * Method returns the user message and displays it on the screen
     *
     * @return String
     */
    private String getInput() {
        String msg = input.getText();
        chat.append("User: " + msg + "\n");
        input.setText("");
        return msg;
    }

    /**
     * Method takes the message and displays it on the screen
     *
     * @param msg String
     */
    public void setMsgToChat(String msg) {
        chat.append("Comp: " + msg + "\n");
    }

    /**
     * Method opens FileChooser and send the path to selected file
     */
    private void openFile() {
        JFileChooser jfc = new JFileChooser();
        int ret = jfc.showOpenDialog(main);
        if (ret == JFileChooser.CANCEL_OPTION) {
            return;
        }
        try {
            String fname = jfc.getSelectedFile().getName();
            main.readFile(fname);
            main.logWriteComp(NEW_FILE + fname);
            setMsgToChat(NEW_FILE + fname);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Talk To Me"
                    + "Error read file!", ex.getMessage(),
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Method displays the last phrase from a collection and records it to log
     * and the program closes
     */
    private void close() {
        setMsgToChat(main.sayGoodBye());
        main.logWriteComp(main.sayGoodBye());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            main.logWriteComp(ex.getMessage());
        }
        this.dispose();
    }

    /**
     * Method show the dialog About
     */
    private void aboutDialog() {
        JOptionPane.showMessageDialog(null, "Talk To Me"
                + " Version 1.0 Frantsuzov S.", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method displays message when the program is stopped
     */
    private void stop() {
        setMsgToChat(MSG_FOR_STOP);
        chat.setEnabled(false);
        button.setEnabled(false);
    }

    /**
     * Method displays message when the program is started
     */
    private void start() {
        setMsgToChat(MSG_FOR_START);
        chat.setEnabled(true);
        button.setEnabled(true);
    }
}
