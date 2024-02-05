import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.NoSuchElementException;

import quiz.Query;
import quiz.QuizSet;
import util.Color;

public class Ask {
    public static final String _NAME = "Questions on Terminal";
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
        // Reading the file with the questions
        try (FileReader fs = new FileReader("questions.txt"); Scanner s = new Scanner(fs)) {
            l++; // Line counting

            String topic = "";
            while(s.hasNextLine()) {
                String line = s.nextLine();
                final int LENGTH = line.length();
                boolean proof = false;

                // Checking if the line begins with "P "
                if (LENGTH >= 3 && line.substring(0, 2).equals("P ")) {
                    proof = true; // The proof is required
                    line = line.substring(2);
                }
                // Checking if the line begins with "Top "
                if (LENGTH >= 5 && line.substring(0, 4).equals("Top ")) {
                    topic = line.substring(4);
                    continue;
                }
                Query q = new Query(topic, line, proof);
                queries.add(q);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Make sure of having 'questions.txt' on this folder. Exit...");
            System.exit(1);
        } catch (NoSuchElementException nsee) {
            System.out.println("Nothing found on line " + l + ". Exiting...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Fatal error... Exit");
            System.exit(1);
        }


        Scanner s = new Scanner(System.in);

        // GAME STARTS
        System.out.println("\nWelcome to " + _NAME + "!");
        System.out.println("I will ask you some random questions and you will have to answer correctly.");
        System.out.println("Let's begin!");

        int j = 0; // counts how many questions have been answered by the user
        do {
            Query q = queries.randomElement();

            System.out.println("\nQuestion n " + ++j);
            if (q.hasTopic())
                System.out.println("Topic: " + Color.makeGreen(q.getTopic()) + "\n");
            System.out.println(q.toString() + "\n");
            
            System.out.println("When you are done, press y to continue .");
            System.out.println("Press any other key to exit.");

            answeredQueries.add(q);
            
            if (queries.isEmpty()) {
                System.out.println("\nHey, hey, hey! Someone told me...");
                System.out.println(Color.makeYellow("You finished the test! Congratulations"));
                System.out.println("Press y to restart.");

                queries = answeredQueries;
                answeredQueries = new QuizSet<Query>();
            }

        } while (wantsToPlay(s));

        s.close();
    }
    private static boolean wantsToPlay(Scanner s) {
        try {
            return s.next().equals("y");
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
