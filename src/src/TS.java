package src;

import java.util.HashMap;

public class TS {

    private HashMap<Token, InfIdentificador> tabelaSimbolos; // Tabela de símbolos do ambiente

    public TS() {
        tabelaSimbolos = new HashMap();

        // Inserindo as palavras reservadas
        Token word;
        word = new Token(Tag.KW_compIf, "if");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compElse, "else");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compClass, "class");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compEnd, "end");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compDef, "def");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compReturn, "return");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compDefstatic, "defstatic");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compVoid, "void");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compMain, "main");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compString, "String");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compBool, "bool");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compInteger, "integer");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compDouble, "double");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compWhile, "while");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compWrite, "write");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compWriteln, "writeln");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compVector, "vector");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compTrue, "true");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compFalse, "false");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compOr, "or");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compAnd, "and");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.ID, "ID");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.ConstInteger, "ConstInteger");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.ConstDouble, "ConstDouble");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.ConstString, "ConstString");
        this.tabelaSimbolos.put(word, new InfIdentificador());

        word = new Token(Tag.KW_compVector, "vector");
        this.tabelaSimbolos.put(word, new InfIdentificador());
    }

    public void put(Token w, InfIdentificador i) {
        tabelaSimbolos.put(w, i);
    }

    // Retorna um identificador de um determinado token
    public InfIdentificador getIdentificador(Token w) {
        InfIdentificador infoIdentificador = (InfIdentificador) tabelaSimbolos.get(w);
        return infoIdentificador;
    }

    // Pesquisa na tabela de símbolos se há algum tokem com determinado lexema
    public Token retornaToken(String lexema) {
        for (Token token : tabelaSimbolos.keySet()) {
            if (token.getLexema().equals(lexema)) {
                return token;
            }
        }
        return null;
    }

    // Pesquisa na tabela de símbolos se há algum tokem com determinado lexema e retorna a tag
    public Tag retornaTag(String lexema) {
        for (Token token : tabelaSimbolos.keySet()) {
            if (token.getLexema().equals(lexema)) {
                return token.getClasse();
            }
        }
        return Tag.KW;
    }

    @Override
    public String toString() {
        String saida = "";
        int i = 1;
        for (Token token : tabelaSimbolos.keySet()) {
            saida += ("posicao " + i + ": \t" + token.toString()) + "\n";
            i++;
        }
        return saida;
    }
}
