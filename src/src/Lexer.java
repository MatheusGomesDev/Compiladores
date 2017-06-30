/*
Falta arrumar na classe sintatico, a questão do erro no while. Sepois implementar a ligação do mains/lexer/sintatico
Em seguida implementar as funções da tabela.
 */
package src;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matheus
 */
public class Lexer {

    private static final int END_OF_FILE = -1; // contante para fim do arquivo
    private static int lookahead = 0; // armazena o último caractere lido do arquivo	
    public static int n_line = 1; // contador de linhas
    public static int n_column = 0; // contador de colunas
    public static int n_error = 0; // contador de erros
    private RandomAccessFile instance_file;
    private boolean comentarioBloco = false;
    private boolean comentarioLinha = false;

    public Lexer(String input_data) {

        // Abre instance_file de input_data
        try {
            instance_file = new RandomAccessFile(input_data, "r");
        } catch (IOException e) {
            System.out.println("Erro de abertura do arquivo " + input_data);
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Erro do programa ou falha da tabela de simbolos");
            System.exit(2);
        }
    }

    // Fecha instance_file de input_data
    public void fechaArquivo() {

        try {
            instance_file.close();
        } catch (IOException errorFile) {
            System.out.println("Erro ao fechar arquivo");
            System.exit(3);
        }
    }

    //Reporta erro para o usuário
    public void sinalizaErro(String mensagem) {
        n_error++;
        System.out.println("[Erro Lexico]: " + mensagem + "\n");
    }

    //Volta uma posição do buffer de leitura
    public void retornaPonteiro() {

        try {
            // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
            if (lookahead != END_OF_FILE) {
                instance_file.seek(instance_file.getFilePointer() - 1);
            }
        } catch (IOException e) {
            System.out.println("Falha ao retornar a leitura");
            System.exit(4);
        }
    }
    
    public int getLinha(){
        return n_line;
    }
    
    public int getErros(){
        return n_error;
    }
    
    public int getColuna(){
        return n_column;
    }

    public void debug(int estado, char c, char l) {
        System.out.println("estado: " + estado);
        System.out.println("c: " + c);
        System.out.println("lookahead: " + l);
        System.exit(0);
    }

    public char verificaProximo() {
        char prox = '\u0000';
        try {
            // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
            if (lookahead != END_OF_FILE) {
                instance_file.seek(instance_file.getFilePointer());
                prox = (char) instance_file.read();
                //retornaPonteiro();

                return prox;
            }
        } catch (IOException e) {
            System.out.println("Falha ao retornar a leitura");
            System.exit(4);
        }
        return prox;
    }

    // Obtém próximo token
    public Token proxToken() {
        TS tabelaSimbolos = new TS();
        StringBuilder lexema = new StringBuilder();
        int estado = 0;
        char c;
        int cont = 1;
        while (true) {
            c = '\u0000'; // null char

            try {
                lookahead = instance_file.read();

                if (lookahead != END_OF_FILE) {
                    c = (char) lookahead;
                }
            } catch (IOException e) {
                System.out.println("Erro na leitura do arquivo");
                System.exit(3);
            }

            switch (estado) {
                case 0:
                    if (lookahead == END_OF_FILE) {
                        if (comentarioBloco == false) {
                            return new Token(Tag.EOF, "EOF");
                        } else {
                            sinalizaErro("Comentario não fechado antes do fim do arquivo na linha " + n_line + ", coluna " + lexema.length());
                            return null;
                        }
                    } else if (comentarioBloco == true) {
                        //se for comentario de bloco ele fica entrando aqui até que encontre */ para poder sair
                        if (c == '*') {
                            char prox = verificaProximo();
                            if (prox == '/') {
                                //retornaPonteiro();
                                comentarioBloco = false;
                                break;
                            }
                        } else if (c == '\n') {
                            n_line++;
                            n_column = 0;
                        }
                    } else if (comentarioLinha == true) {
                        //se for comentario de linha ele fica entrando aqui até que encontre \n para poder sair
                        if (c == '\n') {
                            n_line++;
                            n_column = 0;
                            comentarioLinha = false;
                            break;

                        }
                    } else if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                        // Permance no estado = 1
                        if (c == '\n') {
                            n_line++;
                            n_column = 0;
                        }
                        n_column++;
                    } else if (Character.isLetter(c)) {
                        lexema.append(c);
                        n_column++;
                        estado = 1;
                    } else if (Character.isDigit(c)) {
                        lexema.append(c);
                        n_column++;
                        estado = 3;
                    } else if (c == '<') {
                        estado = 10;
                    } else if (c == '>') {
                        estado = 13;
                    } else if (c == '=') {
                        estado = 29;
                    } else if (c == '!') {
                        estado = 26;
                    } else if (c == '/') {
                        estado = 16;
                    } else if (c == '"') {
                        estado = 8;
                    } else if (c == '*') {
                        estado = 22;
                    } else if (c == ';') {
                        estado = 7;
                        retornaPonteiro();
                    } else if (c == ':') {
                        estado = 20;
                    } else if (c == '.') {
                        estado = 21;
                    } else if (c == '+') {
                        estado = 25;
                    } else if (c == '(') {
                        estado = 32;
                    } else if (c == ')') {
                        estado = 33;
                    } else if (c == ',') {
                        estado = 34;
                    } else if (c == '[') {
                        estado = 35;
                    } else if (c == ']') {
                        estado = 36;
                    } else if (c == '-') {
                        estado = 37;
                    } else {
                        sinalizaErro("Caractere invalido " + c + " na linha " + n_line + ", coluna " + n_column);

                    }
                    break;
                case 1:
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        lexema.append(c);
                        n_column++;
                        // Permanece no estado 1
                        break;
                    } else {
                        //Vai para o estado 2
                        estado = 2;
                    }

                case 2:
                    retornaPonteiro();
                    //verifica se é uma palavra reservada que já está na tabela de simbolos
                    if (tabelaSimbolos.retornaToken(lexema.toString()) != null) {
                        //se sim eu retorno como uma palavra reservada
                        return new Token(tabelaSimbolos.retornaTag(lexema.toString()), lexema.toString());
                    } else {
                        //se não retorno  como ID
                        return new Token(Tag.ID, lexema.toString());
                    }
                case 3:
                    if (Character.isDigit(c)) {
                        // Permanece no estado 3
                        lexema.append(c);
                        n_column++;
                        break;
                    } else if (c == '.') {
                        estado = 5;
                        lexema.append(c);
                        n_column++;
                        break;
                    } else {
                        //Vai para estado 4
                        estado = 4;
                    }

                case 4:
                    if (estado == 4) {
                        retornaPonteiro();
                        return new Token(Tag.ConstInteger, lexema.toString());
                    }

                case 5:
                    if (Character.isDigit(c)) {
                        // Permanece no estado 5
                        lexema.append(c);
                        n_column++;
                        break;
                    } else if (lexema.charAt(lexema.length() - 1) == '.') {
                        //verifica se o ultimo valor entrado é o . e vem outra coisa retorna erro
                        sinalizaErro("Caractere invalido na linha " + n_line + ", coluna " + n_column);
                        lexema = new StringBuilder();
                        estado = 0;
                        break;
                    } else {
                        //Se vier outra coisa vai para o estado 6;
                        estado = 6;

                    }

                case 6:
                    retornaPonteiro();
                    return new Token(Tag.ConstDouble, lexema.toString());
                case 7:
                    return new Token(Tag.PontoVirgula, ";");

                case 8:
                    if (c == '"') {
                        estado = 9;
                    } else if (c == '\n') {
                        sinalizaErro("A String deve ser fechada com aspas antes da quebra de linha");
                        lexema = new StringBuilder();
                        n_line++;
                        n_column = 0;
                        estado = 0;
                        break;
                    } else if (lookahead == END_OF_FILE) {
                        sinalizaErro("A String deve ser fechada com aspas antes do fim do arquivo");

                    } else {
                        lexema.append(c);
                        n_column++;
                        break;
                    }

                case 9:
                    return new Token(Tag.ConstString, lexema.toString());
                case 10:
                    if (c == '=') {
                        estado = 12;
                    } else {
                        estado = 11;

                    }

                case 11:
                    retornaPonteiro();
                    return new Token(Tag.OpMenorQue, "<");
                case 12:
                    return new Token(Tag.OpMenorQueIgual, "<=");
                case 13:
                    if (c == '=') {
                        estado = 14;
                    } else {
                        estado = 15;

                    }

                case 14:
                    return new Token(Tag.OpMaiorQueIgual, ">=");
                case 15:
                    retornaPonteiro();
                    return new Token(Tag.OpMaiorQue, ">");
                case 16:
                    if (c == '*') {
                        comentarioBloco = true;
                        estado = 18;
                    } else if (c == '/') {
                        comentarioLinha = true;
                        estado = 19;
                    } else {
                        estado = 17;

                    }

                case 17:
                    if (estado == 17) {
                        retornaPonteiro();
                        return new Token(Tag.OpDivisao, "/");
                    }
                case 18:
                    if (estado == 18) {
                        //return new Token(Tag.Comentario, "/*");
                        lexema = new StringBuilder();
                        estado = 0;
                        break;
                    }
                case 19:
                    if (estado == 19) {
                        //return new Token(Tag.Comentario, "//");
                        lexema = new StringBuilder();
                        estado = 0;
                        break;
                    }
                case 20:
                    retornaPonteiro();
                    return new Token(Tag.DoisPontos, ":");
                case 21:
                    retornaPonteiro();
                    return new Token(Tag.Ponto, ".");
                case 22:
                    if (c == '/') {
                        estado = 23;
                    } else {
                        estado = 24;
                    }

                case 23:
                    if (estado == 23) {
                        //return new Token(Tag.Comentario, "*/");
                        lexema = new StringBuilder();
                    }
                case 24:
                    retornaPonteiro();
                    return new Token(Tag.OpMultiplicacao, "*");
                case 25:
                    retornaPonteiro();
                    return new Token(Tag.OpSoma, "+");
                case 26:
                    if (c == '=') {
                        estado = 27;
                    } else {
                        estado = 28;
                    }

                case 27:
                    if (estado == 27) {
                        return new Token(Tag.OpDiferente, "!=");
                    }
                case 28:
                    retornaPonteiro();
                    return new Token(Tag.OpUnarioNegacao, "!");
                case 29:
                    if (c == '=') {
                        estado = 30;
                    } else {
                        estado = 31;
                    }

                case 30:
                    if (estado == 30) {
                        return new Token(Tag.OpIgual, "==");
                    }
                case 31:
                    retornaPonteiro();
                    return new Token(Tag.OpAtribuicao, "=");
                case 32:
                    retornaPonteiro();
                    return new Token(Tag.AbreParenteses, "(");
                case 33:
                    retornaPonteiro();
                    return new Token(Tag.FechaParenteses, ")");
                case 34:
                    retornaPonteiro();
                    return new Token(Tag.Virgula, ",");
                case 35:
                    retornaPonteiro();
                    return new Token(Tag.AbreColchetes, "[");
                case 36:
                    retornaPonteiro();
                    return new Token(Tag.FechaColchetes, "]");
                case 37:
                    retornaPonteiro();
                    return new Token(Tag.OpSubtracao, "-");
                case 38:

            }
            cont++;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TS tabelaSimbolos = new TS();
        Lexer lexer = new Lexer("C:\\Users\\piteu\\Desktop\\Trabalho compiladoes Ultimo 24062017\\TrabCompiladoresAAA1\\src\\Errado3Sintatico.txt");
        Sintatico sintatico = new Sintatico(lexer, tabelaSimbolos);  
        sintatico.Programa();
        // Para imprimir a tabela de simbolos, descomente o codigo abaixo
        /*
        System.out.println("");
        System.out.println("Tabela de símbolos:");
        System.out.println(tabelaSimbolos.toString());
        */

    }

}
