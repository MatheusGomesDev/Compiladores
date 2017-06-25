package src;

/**
 *
 * @author matheus
 */
public class Sintatico {

    private final Lexer lexico;
    private Token token;

    public Sintatico(Lexer lexico) {

        this.lexico = lexico;
    }

    // Fecha os arquivos de entrada e de tokens
    public void fechaArquivos() {

        lexico.fechaArquivo();
    }

    public void erroSintatico(String mensagem) {

        System.out.println("Erro sintatico na linha " + lexico.getLinha() + " e coluna " + lexico.getColuna() + ":");
        System.out.println(mensagem + "\n");
    }

    // lexer retorna o proximo token
    public void advance() {
        token = lexico.proxToken();
    }

    public boolean eat(Tag tokenzinho) {

        // enquanto token nao reconhecido (modo panico Lexer)
        /*while (token.getClasse() == Lexer.ERRO) {
            advance();
        }*/

        // se token eh o terminal esperado
        if (token.getClasse() == tokenzinho) {
            advance();
            return true;
        } else {
            return false;
        }
    }
}
