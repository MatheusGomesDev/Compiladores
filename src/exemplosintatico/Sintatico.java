package exemplosintatico;

/**
 *
 * @author gustavo
 */

/* [TODO]
 *
 * 1: Ajustar os erros de acordo com a TP
 * 2: Verificar Recuperacao de Erro:
 ***  2.1: Criar metodos synch() e skip()
 * 3: Ajustar contagem de colunas no lexer
 * 4: Verificar o cadastro dos tokens na Tabela de Simbolos
 *
 */
public class Sintatico {

    private final Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {

        this.lexico = lexico;
    }

    // Fecha os arquivos de entrada e de tokens
    public void fechaArquivos() {

        lexico.fechaArquivo();
    }

    public void erroSintatico(String mensagem) {

        System.out.println("Erro sintatico na linha " + token.getLinha() + " e coluna " + token.getColuna() + ":");
        System.out.println(mensagem + "\n");
    }

    // lexer retorna o proximo token
    public void advance() {
        token = lexico.proxToken();
    }

    public boolean eat(int t) {

        // enquanto token nao reconhecido (modo panico Lexer)
        while (token.getClasse() == Lexico.ERRO) {
            advance();
        }

        // se token eh o terminal esperado
        if (token.getClasse() == t) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    public boolean isToken(int t) {

        return token.getClasse() == t;
    }

    // Programa --> Classe EOF
    public void Programa() {

        advance(); // Leitura inicial obrigatoria do primeiro token

        // Usando o isToken para decidir sobre o FIRST de Programa (conferir TP)
        if (isToken(Token.KW_PUBLIC)) {
            Classe();

            // esperado fim de arquivo
            if (!eat(Token.EOF)) {
                erroSintatico("Esperado fim de arquivo, porem encontrado '" + token.getLexema() + "'");
            }
            System.out.println();
        } else {
            erroSintatico("Esperado 'public', porem encontrado '" + token.getLexema() + "'");
        }
    }

    // Classe --> "public" "class" ID ListaCmd "end"
    public void Classe() {

        if (!eat(Token.KW_PUBLIC)) {
            erroSintatico("Esperado 'public', porem encontrado '" + token.getLexema() + "'");
        }

        if (!eat(Token.KW_CLASS)) {
            erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Token.ID)) {
            erroSintatico("Esperado um identificador, porem encontrado '" + token.getLexema() + "'");
        } else {
            ListaCmd();
            if (!eat(Token.KW_END)) {
                erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
            }
        }
    }

    // ListaCmd --> CmdDispln ListaCmd | epsilon
    public void ListaCmd() {

        // Usando o isToken para decidir sobre o FIRST de CmdDispln (conferir TP)
        if (isToken(Token.KW_SYSOUTDISPLN)) {
            CmdDispln();
            ListaCmd();
        }
        // senao eh um terminal no FIRST entao queremos produzir a cadeia vazia (conferir TP)
    }

    // CmdDispln --> "SystemOutDispln" "(" Expressao ")" ";"
    public void CmdDispln() {

        if (!eat(Token.KW_SYSOUTDISPLN)) {
            erroSintatico("Esperado 'SystemOutDispln', porem encontrado '" + token.getLexema() + "'");
        } else if (!eat(Token.SMB_ABRE_PAREN)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        } else {
            Expressao();
            if (!eat(Token.SMB_FECHA_PAREN)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Token.SMB_PONTO_VIRGULA)) {
                erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
            }
        }
    }

    // Expressao --> Expressao1 Expressao'
    public void Expressao() {

        // Usando o isToken para decidir sobre o FIRST de E (conferir TP)
        if (isToken(Token.CONST_INT) || isToken(Token.ID)) {
            Expressao1();
            ExpressaoLinha();
        } else {
            erroSintatico("Esperado numero inteiro ou identificador, encontrado '" + token.getLexema() + "'");
        }
    }

    // Expressao' --> ">" Expressao1 Expressao'  |  ">=" Expressao1 Expressao'  |  epsilon
    public void ExpressaoLinha() {

        if (eat(Token.OP_MAIOR) || eat(Token.OP_MAIOR_IGUAL)) {
            Expressao1();
            ExpressaoLinha();
        }
    }

    // Expressao1 --> Expressao2 Expressao1'
    public void Expressao1() {

        // Usando o isToken para decidir sobre o FIRST de E1 (conferir TP)
        if (isToken(Token.CONST_INT) || isToken(Token.ID)) {
            Expressao2();
            Expressao1Linha();
        } else {
            erroSintatico("Esperado numero inteiro ou identificador, encontrado '" + token.getLexema() + "'");
        }
    }

    // Expressao1' --> "+" Expressao2 Expressao1'  |  epsilon
    public void Expressao1Linha() {

        if (eat(Token.OP_MAIS)) {
            Expressao2();
            Expressao1Linha();
        }
    }

    // Expressao2 --> Expressao3 Expressao2'
    public void Expressao2() {

        // Usando o isToken para decidir sobre o FIRST de E2 (conferir TP)
        if (isToken(Token.CONST_INT) || isToken(Token.ID)) {
            Expressao3();
            Expressao2Linha();
        } else {
            erroSintatico("Esperado numero inteiro ou identificador, encontrado '" + token.getLexema() + "'");
        }
    }

    // Expressao2' --> "*" Expressao3 Expressao2'  |  epsilon
    public void Expressao2Linha() {

        if (eat(Token.OP_VEZES)) {
            Expressao3();
            Expressao2Linha();
        }
    }

    // Expressao3 --> ConstInt  |  ID
    public void Expressao3() {

        if (!eat(Token.CONST_INT) && !eat(Token.ID)) {
            erroSintatico("Esperado numero inteiro ou identificador, encontrado '" + token.getLexema() + "'");
        }
    }
}
