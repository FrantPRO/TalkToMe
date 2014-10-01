/*
 * Talk To Me
 * Class for write log
 */
package talktome;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frantsuzov S.
 */
public class LogFile {

    static Logger logger;
    final String filename;

    /**
     * Method creates a logger using custom formatter
     */
    public LogFile() {
        logger = Logger.getLogger(LogFile.class.getName());
        logger.setUseParentHandlers(false);
        filename = new SimpleDateFormat("dd.MM.yy_HH-mm-ss").format(System.currentTimeMillis()) + ".log";
        try {
            TxtFormatter txtformatter = new TxtFormatter();
            FileHandler txtfile = new FileHandler(filename);
            txtfile.setFormatter(txtformatter);
            logger.addHandler(txtfile);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Could not create file because of security policy.", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not create file because of an input-output.", e);
        }
    }

    /**
     * Method write message to log
     * @param msg String
     */
    public void write(String msg) {
        logger.log(Level.INFO, msg);
    }
}
