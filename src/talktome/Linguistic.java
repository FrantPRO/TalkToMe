/*
 * Talk To Me
 * linguistic analysis
 */
package talktome;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Frantsuzov S.
 */
public class Linguistic {

    private final TalkToMe main;
    private final Random generator;

    public Linguistic(TalkToMe main) {
        this.main = main;
        generator = new Random();
    }

    public String getAnswer(String question) {
        if (question.contains("?")) {
            return getAnswer(1);
        } else if (question.contains("!")) {
            return getAnswer(2);
        } else {
            return getAnswer(0);
        }
    }

    private String getAnswer(int type) {
        ArrayList<String> answers = main.getAnswers();
        if (type == 1) {
            return answers.get(1 + generator.nextInt(9));
        } else if (type == 2) {
            return answers.get(11 + generator.nextInt(9));
        } else {
            return answers.get(21 + generator.nextInt(9));
        }
    }
}
