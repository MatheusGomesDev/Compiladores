package exemplosintatico;

import java.io.*;
import java.util.HashMap;

/**
 *
 * @author gustavo
 */
public class Lexico {

    private static final int FINAL = -1;
    private static int lido = 0;  // Variavel armazena o último caractere lido do arquivo
    private static int linha = 1;
    private static int coluna = 0;

    private RandomAccessFile arquivo;

    public static HashMap<String, Token> tabela;
    public static int ERRO = 0;

    public Lexico(String entrada) {

        // Abre arquivo de entrada
        try {
            arquivo = new RandomAccessFile(entrada, "r");
            tabela = new HashMap<>();

            tabela.put("public", new Token(Token.KW_PUBLIC, "public", 0, 0));
            tabela.put("class", new Token(Token.KW_CLASS, "class", 0, 0));
            tabela.put("SystemOutDispln", new Token(Token.KW_SYSOUTDISPLN, "SystemOutDispln", 0, 0));
            tabela.put("end", new Token(Token.KW_END, "end", 0, 0));
        } catch (FileNotFoundException erroArquivo) {
            System.out.println("Erro de abertura do arquivo " + entrada + ": " + erroArquivo);
            System.exit(1);
        } catch (Exception erroGeral) {
            System.out.println("Erro do programa ou falha da tabela de simbolos: " + erroGeral);
            System.exit(2);
        }
    }

    // Fecha arquivo de entrada
    public void fechaArquivo() {

        try {
            arquivo.close();
        } catch (IOException erroArquivo) {
            System.out.println("Erro ao fechar arquivo: " + erroArquivo);
            System.exit(3);
        }
    }

    //Reporta erro para o usuário
    public void erroLexico(String mensagem) {

		  System.out.println("Erro lexico na linha " + linha + " e coluna " + coluna + ":");
		  System.out.println(mensagem + "\n");
    }

    //Volta uma posição do carro de leitura
    public void retornaPonteiro() {

        try {
            // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
            if (lido != FINAL) {
                arquivo.seek(arquivo.getFilePointer() - 1);
            }
        } catch (IOException e) {
            System.out.println("Falha ao retornar a leitura");
            System.exit(4);
        }
    }

    // Obtém próximo token
    public Token proxToken() {

        StringBuilder lexema = new StringBuilder();
        int estado = 0;
        char c = ' ';

        while (true) {
            try {
                lido = arquivo.read();
                if (lido != FINAL) {
                    c = (char) lido;
                }
                if (c == '\n') {
                    linha++;
                }
            } catch (IOException e) {
                System.out.println("Erro na leitura do arquivo");
                System.exit(3);
            }

            switch (estado) {
                case 0:
                    if (lido == FINAL) { // Estado 01
                        return new Token(Token.EOF, "EOF", linha, coluna);
                    } else if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
								// Permance no estado = 0
								//if (c == '\n')
                    		//	linha++;
                    } else if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 1;
                    } else if (Character.isLetter(c)) {
                        lexema.append(c);
                        estado = 3;
                    } else if (c == '>') {
                        estado = 5;
                    } else if (c == '(') {
                        // Estado 7
                        return new Token(Token.SMB_ABRE_PAREN, "(", linha, coluna);
                    } else if (c == ')') {
                        // Estado 8
                        return new Token(Token.SMB_FECHA_PAREN, ")", linha, coluna);
                    } else if (c == '+') {
                        // Estado 9
                        return new Token(Token.OP_MAIS, "+", linha, coluna);
                    } else if (c == '*') {
                        // Estado 10
                        return new Token(Token.OP_VEZES, "*", linha, coluna);
                    } else if (c == ';') {
                        // Estado 11
                        return new Token(Token.SMB_PONTO_VIRGULA, ";", linha, coluna);
                    } else if (c == '/') {
                        estado = 12;
                    } else {
                        erroLexico("Caractere Invalido: " + c);
                        return new Token(ERRO, "", 0, 0);
                    }
                    break;
                case 1:
                    if (lido != FINAL && Character.isDigit(c)) {
                        lexema.append(c);
                        // Permanece no estado = 1
                    } else { // Estado 02
                        retornaPonteiro();
                        return new Token(Token.CONST_INT, lexema.toString(), linha, coluna);
                    }
                    break;
                case 3:
                    if (lido != FINAL && (c == '_' || Character.isLetterOrDigit(c))) {
                        lexema.append(c);
                        // Permanece no estado = 3
                    } else { // Estado 04
                        retornaPonteiro();
                        Token token = tabela.get(lexema.toString());
                        if (token == null) {
                            return new Token(Token.ID, lexema.toString(), linha, coluna);
                        }
                        token.setLinha(linha);
                        token.setColuna(coluna);
                        return token;
                    }
                    break;
                case 5:
                    if (lido != FINAL && c == '=') { // Estado 5
                        return new Token(Token.OP_MAIOR_IGUAL, ">=", linha, coluna);
                    } else { // Estado 6
                        retornaPonteiro();
                        return new Token(Token.OP_MAIOR, ">", linha, coluna);
                    }
                case 12:
                    if (lido != FINAL && c == '*') {
                        estado = 13;
                    } else {
                        retornaPonteiro();
                        erroLexico("O comentario deve ser berto com /*");
                        return new Token(ERRO, "", 0, 0);
                    }
                    break;
                case 13:
                    if (lido != FINAL && c == '*') {
                        estado = 14;
                    } else if (lido == FINAL) {
                        erroLexico("O comentario deve ser fechada com */ antes do fim de arquivo");
                        return new Token(ERRO, "", 0, 0);
                    } // Se vier outro, permanece no estado = 12
                    break;
                case 14:
                    if (lido == FINAL) {
                        erroLexico("O comentario deve ser fechada com */ antes do fim de arquivo");
                        return null;
                    } else if (c == '/') {
                        estado = 0;
                    } else {
                        estado = 13;
                    }
                    break;
            }
        }
    }
}
