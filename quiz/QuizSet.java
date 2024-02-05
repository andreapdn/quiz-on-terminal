package quiz;

import java.util.Random;

public class QuizSet<T> extends util.ArraySet<T> {
    /**
     * Returns a random element in the set and removes it from the set.
     * @return An element of the set.
     */
    @SuppressWarnings("unchecked")
    public T randomElement() {
        Random randGen = new Random();
        final int R = randGen.nextInt(vSize);
        return (T) remove(R);
    }
}