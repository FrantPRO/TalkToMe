/*
 * Talk To Me
 * Class for read file
 */
package talktome;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frantsuzov S.
 */
public class ReadFile {

    /**
     * Method reads the file line by line and puts them in a collection
     *
     * @param filename - name of file
     * @return List of phrases
     * @throws java.lang.Exception
     */
    public List<String> getList(String filename) throws Exception {
        BufferedReader reader;
        List<String> lines;
        String line;
            reader = new BufferedReader(new FileReader(filename));
            lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        return lines;
    }
}
