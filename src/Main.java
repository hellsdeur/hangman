import java.io.IOException;
import java.util.Scanner;
/*
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
}*/

// Java program to check the active or available
// ports.

import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        // Creating object of socket class
        Socket portCheck;

        // Defining the hostName to check for port
        // availability
        String host = "localhost";

        if (args.length > 0) {
            host = args[0];
        }
        for (int i = 1024; i < 65536; i++) {
            try {
//                System.out.println("Looking for " + i);
                portCheck = new Socket(host, i);
                System.out.println("Port " + i + " is available");
                portCheck.close();
            }
            catch (UnknownHostException e) {
                System.out.println("Exception occurred" + e);
                break;
            }
            catch (IOException e) {
//                System.out.println("There is a server running on port " + i);
            }
        }
    }
}
