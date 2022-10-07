import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        ServerSocket listenSocket = null;

        try {

            int serverPort = 12536;

            listenSocket = new ServerSocket(serverPort);

            System.out.println("Listening on port " + serverPort);

            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection conn = new Connection(clientSocket);
            }

        } catch (IOException e) {
            System.out.println("Listen: " + e.getMessage());
        } finally {
            try {
                assert listenSocket != null;
                listenSocket.close();
            }
            catch (IOException e) {
                System.out.println("Não é possível fechar a conexão.");
            }
        }
    }
}


class Connection extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            this.start();
        }
        catch (IOException e) {
            System.out.println("Listen: " + e.getMessage());
        }
    }

    public void run() {
        try {
//            server side of the game
            String start = in.readUTF();

            // create a hangman object
            Hangman h = new Hangman(5, "./data/words.csv");

            // generate new game
            h.generateGame();

            // print information about the game
            System.out.println("Categoria: " + h.category);
            System.out.println("Palavra:   " + h.word);

            out.writeUTF(h.category);

            // while there are hearts and the word is not completed
            while (h.hearts > 0 && !h.normalized.equals(h.hidden)) {

                // concatenate
                String reply = h.hidden + "," + h.hearts;
                out.writeUTF(reply);

                String guess = in.readUTF();
                h.update(guess);
            }

            String reply = h.hidden + "," + h.hearts;
            out.writeUTF(reply);
        }
        catch (EOFException e) {
            System.out.println("Listen: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Listen: " + e.getMessage());
        }
        finally {
            try {
                clientSocket.close();
            }
            catch (IOException e) {
                System.out.println("Não é possível fechar a conexão.");
            }
        }
    }
}
