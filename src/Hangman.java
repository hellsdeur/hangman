import java.io.IOException;
import java.sql.SQLOutput;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

public class Hangman {
    protected CSVParser pool;
    protected String category;
    protected String word;

    protected String normalized;
    protected String hidden;
    int hearts;

    public Hangman(int hearts, String path) throws IOException {
        this.pool = new CSVParser(path);
        this.hearts = hearts;
    }

    protected void generateGame() {
        // randomly selects a word and fills its respective empty space

        this.category = pool.randomKey();
        this.word = pool.randomElement(this.category);
        this.normalized = normalize(this.word);
        this.hidden = "_".repeat(this.word.length());
    }

    static String normalize(String word) {
        // transform word into its uppercase form and removes its diacritics

        String upperCased = word.toUpperCase();

        String normalized = Normalizer.normalize(upperCased, Normalizer.Form.NFKD);

        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    protected void update(String guess) {
        String normalizedGuess = normalize(guess);

        if (normalizedGuess.equals(this.normalized)) {
            this.hidden = this.normalized;
        }
        else if (evaluate(normalizedGuess) && normalizedGuess.length() == 1) {
            for (int i = 0; i < this.normalized.length(); ++i) {
                if (this.normalized.toCharArray()[i] == normalizedGuess.toCharArray()[0]) {
                    this.hidden = this.hidden.substring(0, i) + normalizedGuess + this.hidden.substring(i+1);
                }
            }
        }
        else {
            this.hearts -= 1;
        }
    }

    private boolean evaluate(String guess) {
        // checks if guess is correct

        if (this.normalized.contains(guess) && !this.hidden.contains(guess))
            return true;
        return false;
    }
}
