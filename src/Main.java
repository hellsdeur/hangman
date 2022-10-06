import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Hangman h = new Hangman(5, "data/words.csv");
        h.generateGame();

        System.out.println("Categoria: " + h.category);
        System.out.println("Palavra:   " + h.word);

        while (h.hearts > 0 && !h.normalized.equals(h.hidden)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(h.hidden);
            System.out.println("Hearts: " + h.hearts);
            System.out.print("Insira o novo chute: ");
            String guess = scanner.nextLine();

            h.update(guess);

            System.out.println("#".repeat(80));
        }
    }
}