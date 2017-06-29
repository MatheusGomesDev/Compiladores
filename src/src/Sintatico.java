package src;

/**
 *
 * @author matheus
 */
public class Sintatico {

    private final Lexer lexico;
    private Token token;
    private TS tabSimbolos;

    public Sintatico(Lexer lexico, TS tabSimbolos) {

        this.lexico = lexico;
        this.tabSimbolos = tabSimbolos;
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

    public boolean isToken(Tag t) {

        return token.getClasse() == t;
    }

    public void Programa() {

        advance();

        if (isToken(Tag.KW_compClass)) {
            if (!eat(Tag.KW_compClass)) {
                erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.ID)) {
                erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.DoisPontos)) {
                erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
            }
            ListaFuncao();
        } else {
            erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaFuncao() {
        if (isToken(Tag.KW_compDef)) {
            if (!eat(Tag.KW_compDef)) {
                erroSintatico("Esperado 'def', porem encontrado '" + token.getLexema() + "'");
            }
            TipoMacro();
            if (!eat(Tag.ID)) {
                erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.AbreParenteses)) {
                erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
            }
            ListaArg();
            if (!eat(Tag.FechaParenteses)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.FechaParenteses)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.DoisPontos)) {
                erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
            }
            FKDeclaraID();
            ListaCmd();
        }
    }

    public void TipoMacro() {
        if (isToken(Tag.KW_compVoid)) {
            if (!eat(Tag.KW_compVoid)) {
                erroSintatico("Esperado 'void', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compBool)) {
            if (!eat(Tag.KW_compBool)) {
                erroSintatico("Esperado 'bool', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compInteger)) {
            if (!eat(Tag.KW_compInteger)) {
                erroSintatico("Esperado 'integer', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compString)) {
            if (!eat(Tag.KW_compString)) {
                erroSintatico("Esperado 'String', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compDouble)) {
            if (!eat(Tag.KW_compDouble)) {
                erroSintatico("Esperado 'double', porem encontrado '" + token.getLexema() + "'");
            }
        } else {
            erroSintatico("Esperado 'double','void','String','integer', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaArg() {
        if (isToken(Tag.KW_compVoid)) {
            if (!eat(Tag.KW_compVoid)) {
                erroSintatico("Esperado 'void', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compBool)) {
            if (!eat(Tag.KW_compBool)) {
                erroSintatico("Esperado 'bool', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compInteger)) {
            if (!eat(Tag.KW_compInteger)) {
                erroSintatico("Esperado 'integer', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compString)) {
            if (!eat(Tag.KW_compString)) {
                erroSintatico("Esperado 'String', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compDouble)) {
            if (!eat(Tag.KW_compDouble)) {
                erroSintatico("Esperado 'double', porem encontrado '" + token.getLexema() + "'");
            }
        }
    }

    public void FKDeclaraID() {
        if (isToken(Tag.KW_compVoid)) {
            if (!eat(Tag.KW_compVoid)) {
                erroSintatico("Esperado 'void', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compBool)) {
            if (!eat(Tag.KW_compBool)) {
                erroSintatico("Esperado 'bool', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compInteger)) {
            if (!eat(Tag.KW_compInteger)) {
                erroSintatico("Esperado 'integer', porem encontrado '" + token.getLexema() + "'");
            }

        } else if (isToken(Tag.KW_compString)) {
            if (!eat(Tag.KW_compString)) {
                erroSintatico("Esperado 'String', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compDouble)) {
            if (!eat(Tag.KW_compDouble)) {
                erroSintatico("Esperado 'double', porem encontrado '" + token.getLexema() + "'");
            }
        }
    }

    public void ListaCmd() {
        if (isToken(Tag.KW_compIf)) {
            if (!eat(Tag.KW_compIf)) {
                erroSintatico("Esperado 'if', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.AbreParenteses)) {
                erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.FechaParenteses)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
        }

    }

    public void Expressao() {
        if (isToken(Tag.AbreParenteses)) {
            if (!eat(Tag.AbreParenteses)) {
                erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.FechaParenteses)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.ID)) {
            if (!eat(Tag.ID)) {
                erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao4Linha();
        } else if (isToken(Tag.ConstInteger)) {

        } else if (isToken(Tag.ConstDouble)) {

        } else if (isToken(Tag.ConstString)) {

        } else if (isToken(Tag.KW_compTrue)) {

        } else if (isToken(Tag.KW_compFalse)) {

        } else if (isToken(Tag.KW_compVector)) {

        } else if (isToken(Tag.OpSubtracao)) {

        } else if (isToken(Tag.OpUnarioNegacao)) {

        }
    }

    public void Expressao4Linha() {
        if (isToken(Tag.AbreParenteses)) {
            if (!eat(Tag.AbreParenteses)) {
                erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
            }
            QBExp();
        }
    }

    public void QBExp() {
        if (isToken(Tag.AbreParenteses)) {
            if (!eat(Tag.AbreParenteses)) {
                erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.FechaParenteses)) {
                erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.ID)) {
            if (!eat(Tag.ID)) {
                erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao4Linha();
        } else if (isToken(Tag.ConstInteger)) {

        } else if (isToken(Tag.ConstDouble)) {

        } else if (isToken(Tag.ConstString)) {

        } else if (isToken(Tag.KW_compTrue)) {

        } else if (isToken(Tag.KW_compFalse)) {

        } else if (isToken(Tag.KW_compVector)) {

        } else if (isToken(Tag.OpSubtracao)) {

        } else if (isToken(Tag.OpUnarioNegacao)) {

        }
    }

    public void Funcao1() {
        if (!eat(Tag.KW_compClass)) {
            erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.ID)) {
            erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.DoisPontos)) {
            erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
        }
        ListaFuncao();
        //Main();
        if (!eat(Tag.KW_compEnd)) {
            erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.Ponto)) {
            erroSintatico("Esperado '.', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao2() {

        if (!eat(Tag.KW_compDef)) {
            erroSintatico("Esperado 'def', porem encontrado '" + token.getLexema() + "'");
        }
        TipoMacro();
        if (!eat(Tag.ID)) {
            erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        ListaArg();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.DoisPontos)) {
            erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
        }
        FKDeclaraID();
        ListaCmd();
        //Retorno();
        if (!eat(Tag.KW_compEnd)) {
            erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao3() {
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        QBExp();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao4() {
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao5() {
        if (!eat(Tag.KW_compDefstatic)) {
            erroSintatico("Esperado 'defstatic', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.KW_compVoid)) {
            erroSintatico("Esperado 'void', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.KW_compMain)) {
            erroSintatico("Esperado 'main', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.KW_compString)) {
            erroSintatico("Esperado 'string', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreColchetes)) {
            erroSintatico("Esperado '[', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.FechaColchetes)) {
            erroSintatico("Esperado ']', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.ID)) {
            erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.DoisPontos)) {
            erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
        }
        FKDeclaraID();
        ListaCmd();
        if (!eat(Tag.KW_compEnd)) {
            erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao6() {
        if (!eat(Tag.KW_compVoid)) {
            erroSintatico("Esperado 'void', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao7() {
        if (!eat(Tag.AbreColchetes)) {
            erroSintatico("Esperado '[', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaColchetes)) {
            erroSintatico("Esperado ']', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.OpAtribuicao)) {
            erroSintatico("Esperado '=', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public boolean Funcao8() {
        if (isToken(Tag.KW_compBool)) {
            if (!eat(Tag.KW_compBool)) {
                erroSintatico("Esperado 'bool', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compInteger)) {
            if (!eat(Tag.KW_compInteger)) {
                erroSintatico("Esperado 'integer', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compString)) {
            if (!eat(Tag.KW_compString)) {
                erroSintatico("Esperado 'string', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compDouble)) {
            if (!eat(Tag.KW_compDouble)) {
                erroSintatico("Esperado 'double', porem encontrado '" + token.getLexema() + "'");
            }
        } else {
            return false;
        }
        return true;
    }

    public void Funcao9() {
        if (!eat(Tag.KW_compIf)) {
            erroSintatico("Esperado 'if', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.DoisPontos)) {
            erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
        }
        ListaCmd();
        //CmdIFLinha();
    }

    public boolean Funcao10() {
        if (isToken(Tag.KW_compWhile)) {
            Funcao11();
        } else if (isToken(Tag.KW_compWrite)) {
            Funcao12();
        } else if (isToken(Tag.KW_compWriteln)) {
            Funcao13();
        } else {
            return false;
        }
        return true;
    }

    public void Funcao11() {
        if (!eat(Tag.KW_compWhile)) {
            erroSintatico("Esperado 'while', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.DoisPontos)) {
            erroSintatico("Esperado ':', porem encontrado '" + token.getLexema() + "'");
        }
        ListaCmd();
        if (!eat(Tag.KW_compEnd)) {
            erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao12() {
        if (!eat(Tag.KW_compWrite)) {
            erroSintatico("Esperado 'write', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao13() {
        if (!eat(Tag.KW_compWriteln)) {
            erroSintatico("Esperado 'write', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.AbreParenteses)) {
            erroSintatico("Esperado '(', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.FechaParenteses)) {
            erroSintatico("Esperado ')', porem encontrado '" + token.getLexema() + "'");
        }
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao14() {
        if (!eat(Tag.OpAtribuicao)) {
            erroSintatico("Esperado '=', porem encontrado '" + token.getLexema() + "'");
        }
        Expressao();
        if (!eat(Tag.PontoVirgula)) {
            erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao15() {
        if (!eat(Tag.ID)) {
            erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
        }
        //CmdAfLinha();
    }

    public boolean Funcao16() {
        if (isToken(Tag.ID)) {
            if (!eat(Tag.ID)) {
                erroSintatico("Esperado 'ID', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao4Linha();
        } else if (isToken(Tag.ConstInteger)) {
            if (!eat(Tag.ConstInteger)) {
                erroSintatico("Esperado 'ConstInteger', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.ConstDouble)) {
            if (!eat(Tag.ConstDouble)) {
                erroSintatico("Esperado 'ConstDouble', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.ConstString)) {
            if (!eat(Tag.ConstString)) {
                erroSintatico("Esperado 'ConstString', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compTrue)) {
            if (!eat(Tag.KW_compTrue)) {
                erroSintatico("Esperado 'true', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compFalse)) {
            if (!eat(Tag.KW_compFalse)) {
                erroSintatico("Esperado 'false', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compVector)) {
            if (!eat(Tag.KW_compVector)) {
                erroSintatico("Esperado 'vector', porem encontrado '" + token.getLexema() + "'");
            }
            //TipoPrimitivo();
            if (!eat(Tag.AbreColchetes)) {
                erroSintatico("Esperado '[', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.FechaColchetes)) {
                erroSintatico("Esperado ']', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpUnarioNegativo)) {
            if (!eat(Tag.OpUnarioNegativo)) {
                erroSintatico("Esperado '-', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpUnarioNegacao)) {
            if (!eat(Tag.OpUnarioNegacao)) {
                erroSintatico("Esperado '!', porem encontrado '" + token.getLexema() + "'");
            }
        } else {
            return false;
        }
        return true;
    }

}
