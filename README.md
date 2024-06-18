# Programa-o-Concorrente_ATVColetivaN2
Estrutura do Projeto
src
cliente
ClienteBiblioteca.java - Classe que implementa o cliente que se comunica com o servidor.


servidor

ServidorBiblioteca.java - Classe principal do servidor que gerencia as conexões e as operações dos livros.
GerenciadorLivros.java - Classe que gerencia as operações de manipulação dos livros.
Livro.java - Classe que representa um livro.


resources

livros.json - Arquivo JSON que armazena a coleção inicial de livros.


Funcionalidades

Listar livros: Lista todos os livros disponíveis na biblioteca.
Alugar livro: Aluga um livro específico, reduzindo o número de exemplares disponíveis.
Devolver livro: Devolve um livro alugado, aumentando o número de exemplares disponíveis.
Cadastrar livro: Adiciona um novo livro à biblioteca.
Remover livro: Remove um livro da biblioteca.


Pré-requisitos

Java 8 ou superior
Eclipse IDE ou outra IDE de sua escolha
Biblioteca Gson (já incluída no caminho de construção do projeto)
Configuração


Clone o repositório:

Copiar código
git clone https://github.com/seu-usuario/BibliotecaServidor.git
cd BibliotecaServidor

Importe o projeto para a sua IDE:

Abra o Eclipse ou outra IDE de sua escolha.
Importe o projeto como um projeto Java existente.
Adicione a biblioteca Gson ao projeto:

Baixe a biblioteca Gson aqui.
Adicione o arquivo JAR ao caminho de construção do projeto.
Uso

Inicie o servidor:

No Eclipse, clique com o botão direito na classe ServidorBiblioteca e selecione Run As > Java Application.
Verifique o console para a mensagem "Servidor iniciado na porta 12346".
Inicie o cliente:

No Eclipse, clique com o botão direito na classe ClienteBiblioteca e selecione Run As > Java Application.
No console do cliente, digite os seguintes comandos para interagir com o servidor:

Comandos

Listar livros:

------listar-------

Alugar livro:

------alugar [Título do Livro]--------

Devolver livro:

------devolver [Título do Livro]------

Cadastrar livro:

cadastrar
Em seguida, siga as instruções para digitar os detalhes do livro:
Autor
Título
Gênero
Número de Exemplares

Remover livro:

remover [Título do Livro]
Sair do sistema:



sair('sair)
