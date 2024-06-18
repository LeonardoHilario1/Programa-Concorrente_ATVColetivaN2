package Cliente;

import java.io.*;
import java.net.*;

public class ClienteBiblioteca {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12346;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            String serverResponse;
            boolean running = true;

            while (running) {
                System.out.println("Digite uma operação (listar, alugar, devolver, cadastrar, sair):");
                userInput = consoleInput.readLine();

                if (userInput.equalsIgnoreCase("sair")) {
                    running = false;
                    out.println("sair");
                    System.out.println("Saindo do sistema...");
                } else if (userInput.equalsIgnoreCase("cadastrar")) {
                    System.out.println("Digite o autor do livro:");
                    String autor = consoleInput.readLine();
                    System.out.println("Digite o título do livro:");
                    String titulo = consoleInput.readLine();
                    System.out.println("Digite o gênero do livro:");
                    String genero = consoleInput.readLine();
                    System.out.println("Digite o número de exemplares:");
                    int exemplares = Integer.parseInt(consoleInput.readLine());

                    String livroJson = String.format(
                        "{\"autor\":\"%s\", \"titulo\":\"%s\", \"genero\":\"%s\", \"exemplares\":%d}",
                        autor, titulo, genero, exemplares
                    );
                    out.println("cadastrar " + livroJson);

                   
                    serverResponse = in.readLine();
                    System.out.println("Resposta do servidor: " + serverResponse);
                } else {
                    out.println(userInput);

                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("- " + serverResponse);
                        if (!in.ready()) break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
