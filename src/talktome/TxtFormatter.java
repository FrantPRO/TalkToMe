/*
 * Talk To Me
 * Class for formate text to output log
 */
package talktome;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author Frantsuzov S.
 */
public class TxtFormatter extends Formatter {

    /**
     * Variable to hold a line feed
     */
    String newLine = System.getProperties().getProperty("line.separator");

    /**
     * Return the header of the file
     * @return String
     */
    @Override
    public String getHead(Handler h) {
        return "STARTING LOG FILE" + newLine;
    }

    /**
     * Return the file ending
     * @return String
     */
    @Override
    public String getTail(Handler h) {
        return "END OF FILE";
    }

    /**
     * Format the message in one line
     * @return String
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder result = new StringBuilder();
        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(System.currentTimeMillis());
        result.append(date).append(": ").append(record.getMessage()).append(newLine);
        return result.toString();
    }
}
