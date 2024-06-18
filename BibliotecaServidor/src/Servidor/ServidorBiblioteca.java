package Servidor;

import java.io.*;
import java.net.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServidorBiblioteca {
    private static final int PORT = 12346;
    private GerenciadorLivros gerenciadorLivros;

    public static void main(String[] args) {
        new ServidorBiblioteca().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

          
            carregarLivros();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarLivros() {
        try (Reader reader = new FileReader("src/resources/livros.json")) {
            List<Livro> livros = new Gson().fromJson(reader, new TypeToken<List<Livro>>() {}.getType());
            gerenciadorLivros = new GerenciadorLivros(livros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request;
                while ((request = in.readLine()) != null) {
                    if (request.equalsIgnoreCase("sair")) {
                        System.out.println("Cliente desconectado.");
                        break;
                    }
                    System.out.println("Comando recebido : " + request);
                    
                    String response = processRequest(request);
                    out.println(response);
                    System.out.println("Resposta enviada ao cliente: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processRequest(String request) {
            String[] parts = request.split(" ", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : null;

            switch (command) {
                case "listar":
                    return listarLivros();
                case "alugar":
                    if (argument != null && !argument.trim().isEmpty()) {
                        return gerenciadorLivros.alugarLivro(argument);
                    } else {
                        return " O Comando alugar precisa de um título de livro.";
                    }
                case "devolver":
                    if (argument != null && !argument.trim().isEmpty()) {
                        return gerenciadorLivros.devolverLivro(argument);
                    } else {
                        return " O Comando devolver precisa de um título de livro.";
                    }
                case "cadastrar":
                    if (argument != null && !argument.trim().isEmpty()) {
                        Livro novoLivro = new Gson().fromJson(argument, Livro.class);
                        gerenciadorLivros.cadastrarLivro(novoLivro);
                        return "Livro cadastrado com sucesso.";
                    } else {
                        return " O Comando cadastrar precisa de um livro em formato JSON.";
                    }
                default:
                    return "Comando desconhecido.";
            }
        }

        private String listarLivros() {
            List<Livro> livros = gerenciadorLivros.listarLivros();
            StringBuilder sb = new StringBuilder();
            for (Livro livro : livros) {
                sb.append("Título: ").append(livro.getTitulo()).append("\n");
                sb.append("Autor: ").append(livro.getAutor()).append("\n");
                sb.append("Gênero: ").append(livro.getGenero()).append("\n");
                sb.append("Exemplares: ").append(livro.getExemplares()).append("\n\n");
            }
            return sb.toString();
        }
    }
}
