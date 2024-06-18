package Servidor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GerenciadorLivros {
    private List<Livro> livros;

    public GerenciadorLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public synchronized String alugarLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                livro.setExemplares(livro.getExemplares() - 1);
                atualizarArquivo();
                return "Livro alugado com sucesso.";
            }
        }
        return "Livro não esta disponível para aluguel.";
    }

    public synchronized String devolverLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setExemplares(livro.getExemplares() + 1);
                atualizarArquivo();
                return "Livro devolvido com sucesso.";
            }
        }
        return "Livro não encontrado no sistema.";
    }

    public synchronized String cadastrarLivro(Livro novoLivro) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(novoLivro.getTitulo())) {
                return "Livro já cadastrado no sistema.";
            }
        }
        livros.add(novoLivro);
        atualizarArquivo();
        return "Livro cadastrado com sucesso.";
    }

    private void atualizarArquivo() {
        try (FileWriter writer = new FileWriter("src/resources/livros.json")) {
            new Gson().toJson(livros, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
