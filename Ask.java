import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.NoSuchElementException;

import quiz.Query;
import quiz.QuizSet;
import util.Color;
import quiz.Query.TooManyAnswersException;

public class Ask {
    public static final String _NAME = "Quiz on Terminal";
    public static final String _VERSION = "2.0";

    private static QuizSet<Query> queries;
    private static QuizSet<Query> answeredQueries;

    public static void main(String[] args) {
        // VERSIONING
        if (args.length >= 1) {
            if (args[0].equals("--version") || args[0].equals("-v")) {
                System.out.println(_VERSION);
                System.exit(0);
            } else {
                System.out.println("Unknown arguments.");
                System.exit(1);
            }
        }

        // INITIALIZING SETS
        queries = new QuizSet<Query>();
        answeredQueries = new QuizSet<Query>();

        int l = 0;
        // PARSING DATA
        try (FileReader fs = new FileReader("questions.txt"); Scanner s = new Scanner(fs)) {
            l++; // Line counting

            String topic = "";
            Query q = null;
            while(s.hasNextLine()) {
                String line = s.nextLine();
                final int LENGTH = line.length();

                if (LENGTH >= 5 && line.substring(0, 4).equals("Top ")) {
                    topic = line.substring(4);
                    continue;
                }
                if (LENGTH >= 3 && line.substring(0, 2).equals("- ")) { // Incorrect answer
                    line = line.substring(2);
                    q.addAnswer(line, false);
                    continue;
                }
                if (LENGTH >= 3 && line.substring(0, 2).equals("+ ")) { // Correct answer
                    line = line.substring(2);
                    q.addAnswer(line, true);
                    continue;
                }
                q = new Query(topic, line);
                queries.add(q);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Make sure of having 'questions.txt' on this folder. Exit...");
            System.exit(1);
        } catch (NoSuchElementException nsee) {
            System.out.println("Nothing found on line " + l + ". Exiting...");
            System.exit(1);
        } catch (TooManyAnswersException tmae) {
            System.out.println("The maximum of answers per question is 26. Remove some answers from line " + l + ".");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Fatal error... Exit");
            System.exit(1);
        }


        Scanner s = new Scanner(System.in);

        // GAME STARTS
        System.out.println("\nWelcome to " + _NAME + ", the funniest quiz ever!");
        System.out.println("I will ask you some random questions and you will have to answer correctly.");
        System.out.println("Let's begin!");

        int j = 0; // counts how many questions have been answered by the user
        do {
            Query q = queries.randomElement();

            System.out.println("\nQuestion n " + ++j);
            if (q.hasTopic())
                System.out.println("Topic: " + Color.makeGreen(q.getTopic()) + "\n");
            System.out.println(q.toString() + "\n");
            
            System.out.println("What's the correct answer?");
            System.out.println("Write exit to end the quiz and see your results.");

            // Answer parsing:
            do {
                String answ = null;
                try { answ = s.next(); } catch (NoSuchElementException e) { continue; }
                // Exiting if user writes 'exit':
                if (answ.equals("exit")) printResults();
                if (answ.length() > 1) {
                    System.out.println("You can only write one letter.");
                    continue;
                }
                try { q.addUserAnswer(answ.toLowerCase().charAt(0)); } // Adding user answer
                catch (IndexOutOfBoundsException e) {
                    System.out.println("Out of range answer. Try again.");
                    continue;
                }
                answeredQueries.add(q); // Adding to answered queries
                break;
            } while (true);

        } while (!(queries.isEmpty()));

        System.out.println("\nHey, hey, hey! Someone told me...");
        System.out.println(Color.makeYellow("You finished the test! Congratulations\n"));

        printResults();

        s.close();
    }

    private static void printResults() {
        Object[] answers = answeredQueries.toArray();
        if (answers.length > 0) System.out.println("\nLet's see what you've done!\n");
        for (int k = 0; k < answers.length; k++) {
            Query q = ((Query) answers[k]);
            String result = q.hasUserAnsweredCorrectly() ?
                Color.makeGreen("CORRECT") :
                Color.makeRed("WRONG");
            System.out.println("Question n " + (k + 1) + " " + result);
            System.out.println(q.toCorrectionString());
        }
        System.exit(0);
    }
}
