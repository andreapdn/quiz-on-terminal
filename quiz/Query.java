package quiz;

import java.util.Random;

import util.ArraySet;
import util.Color;

public class Query {
    private String topic;
    private String question;
    private ArraySet<Answer> answers;
    private ArraySet<Integer> order;
    private int userAnswer;

    private Random randGen;

    public Query(String topic, String question) {
        this.topic = topic;
        this.question = question;

        this.userAnswer = -1;
        randGen = new Random();
        answers = new ArraySet<Answer>();
        order = new ArraySet<Integer>();
    }
    public String getTopic() {
        return topic;
    }
    public String getQuestion() {
        return question;
    }
    public boolean hasTopic() {
        return !(topic == null || topic.length() == 0);
    }

    /**
     * Adds an answer to the possible answers list for this query.
     * @param text The text of the answer.
     * @param isCorrect The answer is correct?
     * @throws CouldNotAddAnswersAnymoreException if user has already answered.
     * @throws TooManyAnswersException if the number of added answers >= 26 (n of alphabet letters)
     * The number of added answers can be returned by getAnswersSize() method.
     */
    public void addAnswer(String text, boolean isCorrect) {
        if (hasUserAnswered()) {
            throw new CouldNotAddAnswersAnymoreException();
        }
        if (answers.size() >= 26) {
            throw new TooManyAnswersException();
        }
        answers.add(new Answer(text, isCorrect));
    }
    public int getAnswersSize() {
        return answers.size();
    }
    public boolean hasUserAnswered() {
        return userAnswer >= 0;
    }
    /**
     * Adds the user answer. This is unique.
     * If this method get called twice, the first user answer will be overwritten.
     * @param index The index (in the user's order) of the answer
     * @throws IndexOutOfBoundsException if the index < 0 || index >= getAnswersSize()
     */
    public void addUserAnswer(int index) {
        if (order.isEmpty()) newOrder();

        if (index < 0 || index >= getAnswersSize()) {
            throw new IndexOutOfBoundsException();
        }
        userAnswer = index;
    }
    /**
     * Adds the user answer. This is unique.
     * If this method get called twice, the first user answer will be overwritten.
     * @param index The letter (in the user's order) of the answer
     * @throws IndexOutOfBoundsException if the index < 'a' || index >= [the last answer letter]
     */
    public void addUserAnswer(char ch) {
        addUserAnswer(ch - 'a');
    }
    /**
     * Checks whether the user's answer is correct.
     * @return True if user is correct, false if he is incorrect.
     * @throws UserHasNotAnsweredException if user has not answered.
     */
    public boolean hasUserAnsweredCorrectly() {
        if (!(hasUserAnswered())) {
            throw new UserHasNotAnsweredException();
        }
        return isCorrectAnswer(userAnswer);
    }
    private boolean isCorrectAnswer(int index) {
        return ((Answer) answers.get(f(index))).isCorrect();
    }

    /**
     * Turns the user's answers order into the order in which answers have been added.
     * @param x Index of the answer in the user's order
     * @return The index of the answer in the order it's been added
     */
    private int f(int x) {
        return ((Integer) (order.get(x))).intValue();
    }

    public String toString() {
        if (order.isEmpty()) newOrder();

        String query = "> " + Color.makeYellow(question) + "\n";

        String[] answers = toStringAnswers();
        for (int i = 0; i < answers.length; i++) {
            query += answers[i] + "\n";
        }

        return query;
    }
    
    public String toCorrectionString() {
        if (!(hasUserAnswered())) {
            throw new UserHasNotAnsweredException();
        }

        String query = "> " + Color.makeYellow(question) + "\n";

        String[] answers = toStringAnswers();
        for (int i = 0; i < answers.length; i++) {
            // Write X
            if (i == userAnswer) {
                answers[i] = answers[i] + "  X";
            }
            // Highlights in RED the INCORRECT user answer
            if (i == userAnswer && !(hasUserAnsweredCorrectly())) {
                answers[i] = Color.makeRed(answers[i]);
            }
            // Highlights in GREEN the CORRECT answerS
            if (isCorrectAnswer(i)) {
                answers[i] = Color.makeGreen(answers[i]);
            }
            query += answers[i] + "\n";
        }

        return query;
    }
    private String[] toStringAnswers() {
        String[] lines = new String[answers.size()];
        char lett = 'a';

        for (int i = 0; i < answers.size(); i++) {
            int newInd = ((Integer) order.get(i)).intValue();
            Answer answ = ((Answer) answers.get(newInd));

            lines[i] = " " + (lett++) + ") " + answ.getText();
        }

        return lines;
    }

    private void newOrder() {
        final int ANSWERS_SIZE = answers.size();
        while (order.size() < ANSWERS_SIZE) {
            final int NEW_NUMBER = randGen.nextInt(ANSWERS_SIZE);
            if (!(order.contains(NEW_NUMBER)))
                order.add(NEW_NUMBER);
        }
    }

    
    public class TooManyAnswersException extends RuntimeException {}
    class UserHasNotAnsweredException extends RuntimeException {}
    class CouldNotAddAnswersAnymoreException extends RuntimeException {}
}

class Answer {
    private String answer;
    private boolean correct;

    public Answer(String text, boolean isCorrect) {
        answer = text;
        correct = isCorrect;
    }
    public String getText() {
        return answer;
    }
    public boolean isCorrect() {
        return correct;
    }
}