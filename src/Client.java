import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            int serverPort = 12536;
            clientSocket = new Socket("localhost", serverPort);

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

//            client side of the game
            out.writeUTF("Iniciando conexão");

            // listening to the category
            String category = in.readUTF();

            // listening to the hidden and hearts for the 1st time
            String serverReply  = in.readUTF();
            String[] parts = serverReply.split(",");
            String hidden = parts[0];
            String hearts = parts[1];

            // start of the game
            System.out.print("=".repeat(39));
            System.out.print(" NOVO JOGO ");
            System.out.println("=".repeat(40));
            int h = Integer.parseInt(hearts);
            System.out.println("Categoria: "+ category +"\n");
            System.out.println("♥".repeat(h));
            System.out.println(hidden);

            // calculate underlines
            String notUnderlines = hidden.replace("_", "");
            int countUnderlines = hidden.length() - notUnderlines.length();

            // initiate round count
            int round = 1;

            // while there are hearts and the word is not completed
            while (h > 0 && countUnderlines != 0) {

                // which round
                System.out.print("=".repeat(40));
                System.out.print(" RODADA " + round + " ");
                System.out.println("=".repeat(40));

                // get user input
                Scanner scanner = new Scanner(System.in);
                System.out.print("Insira o novo chute: ");
                String guess = scanner.nextLine();

                // send input to server
                out.writeUTF(guess);

                // listen reply and parse arguments
                serverReply = in.readUTF();
                parts = serverReply.split(",");
                hidden = parts[0];
                hearts = parts[1];

                h = Integer.parseInt(hearts);

                // print reply
                System.out.println("♥".repeat(h));
                System.out.println(hidden);

                // calculate underlines
                notUnderlines = hidden.replace("_", "");
                countUnderlines = hidden.length() - notUnderlines.length();

                // increase round
                round++;
            }

            // the user won
            if (countUnderlines == 0) {
                System.out.println("\n✨✨✨ Você GANHOU ✨✨✨");
            }
            // the user lost
            if (h == 0) {
                System.out.println("\n\uD83E\uDD22\uD83E\uDD22\uD83E\uDD22 Você PERDEU \uD83E\uDD22\uD83E\uDD22\uD83E\uDD22");
            }
        }
        catch (UnknownHostException e) {
            System.out.println("Socket" + e.getMessage());
        }
        catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }

        finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                }
                catch (IOException e) {
                    System.out.println("Não é possível fechar a conexão.");
                }
            }
        }
    }
}
