package src;

/**
 *
 * @author matheus
 */
public class Sintatico {

    private final Lexer lexico;
    private Token token;
    private TS tabSimbolos;
    private int errosSintaticos = 0;
    private int errosLexicos = 0;

    public Sintatico(Lexer lexico, TS tabSimbolos) {

        this.lexico = lexico;
        this.tabSimbolos = tabSimbolos;
    }

    // Fecha os arquivos de entrada e de tokens
    public void fechaArquivos() {

        lexico.fechaArquivo();
    }

    public void erroSintatico(String mensagem) {
        this.errosSintaticos++;
        System.out.println("Erro sintatico na linha " + lexico.getLinha() + " e coluna " + lexico.getColuna() + ":");
        System.out.println(mensagem + "\n");
        if(this.errosSintaticos == 5){
            System.out.println("O número de erros sintáticos atingiu o limite de 5. O analisador sintático será abortado!!");
        }
    }

    // lexer retorna o proximo token
    public void advance() {
        token = lexico.proxToken();
        if (token != null) {
            System.out.println("Token: " + token.toString() + "\t Linha: " + lexico.getLinha());
        }
        if (tabSimbolos.retornaToken(token.getLexema()) == null) {
            tabSimbolos.put(token, new InfIdentificador());
        }

    }

    public boolean eat(Tag tokenzinho) {

        // enquanto token nao reconhecido (modo panico Lexer)
        while(lexico.getErros() > this.errosLexicos) {
            advance();
            this.errosLexicos = lexico.getErros();
        }
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
            Funcao1();
        } else if (isToken(Tag.EOF)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
        }
        
        System.out.println("Analise sintatica concluida com sucesso!");
    }

    public void Classe() {
        if (isToken(Tag.KW_compClass)) {
            Funcao1();
        } else if (isToken(Tag.EOF)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'class', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void DeclaraID() {
        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função DeclaraID");
            }
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'end/return/void/bool/integer/string/double/if/else/while/write/writeln/ID', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaFuncao() {
        if (isToken(Tag.KW_compDef)) {
            Funcao2();
        } else if (!isToken(Tag.KW_compDefstatic)) {
            //skip()
            erroSintatico("Esperado 'def/defstatic', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaFuncaoLinhha() {
        if (isToken(Tag.KW_compDef)) {
            Funcao2();
        } else if (!isToken(Tag.KW_compDefstatic)) {
            //skip()
            erroSintatico("Esperado 'def/defstatic', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Funcao() {
        if (isToken(Tag.KW_compDef)) {
            Funcao2();
        } else if (isToken(Tag.KW_compDefstatic)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'def/defstatic', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void FKDeclaraID() {
        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função FKDeclaraID");
            }
        } else if (!(isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID))) {
            //skip()
            erroSintatico("Esperado 'end/return/void/bool/integer/string/double/if/else/while/write/writeln/ID', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaArg() {
        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função ListaArg");
            }
        } else if (!isToken(Tag.FechaParenteses)) {
            //skip()
            erroSintatico("Esperado 'void/bool/integer/string/double/)', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaArgLinha() {
        if (isToken(Tag.Virgula)) {
            if (!eat(Tag.Virgula)) {
                erroSintatico("Esperado ',', porem encontrado '" + token.getLexema() + "'");
            }
            ListaArg();
        } else if (!isToken(Tag.FechaParenteses)) {
            //skip()
            erroSintatico("Esperado ',/)', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Arg() {

        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função ListaArg");
            }
        } else if (isToken(Tag.FechaParenteses) || isToken(Tag.Virgula)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'void/bool/integer/string/double/)/,', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Retorno() {
        if (isToken(Tag.KW_compReturn)) {
            if (!eat(Tag.KW_compReturn)) {
                erroSintatico("Esperado 'return', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.PontoVirgula)) {
                erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (!isToken(Tag.KW_compEnd)) {
            //skip()
            erroSintatico("Esperado 'return/end', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Main() {
        if (isToken(Tag.KW_compEnd)) {
            //sync()
        } else if (isToken(Tag.KW_compDefstatic)) {
            Funcao5();
        } else {
            //skip()
            erroSintatico("Esperado 'end/defstatic', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void TipoMacro() {
        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função TipoMacro");
            }
        } else if (isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'void/bool/integer/string/double', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void TipoMacroLinha() {
        if (isToken(Tag.AbreColchetes)) {
            if (!eat(Tag.AbreColchetes)) {
                erroSintatico("Esperado '[', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.FechaColchetes)) {
                erroSintatico("Esperado ']', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (!isToken(Tag.ID)) {
            //skip()
            erroSintatico("Esperado '[/]', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void TipoPrimitivo() {
        if (isToken(Tag.KW_compVoid)) {
            Funcao6();
        } else if (isToken(Tag.KW_compBool) || isToken(Tag.KW_compInteger) || isToken(Tag.KW_compString) || isToken(Tag.KW_compDouble)) {
            if (!Funcao8()) {
                erroSintatico("Ocorreu um erro sintatico na função TipoPrimitivo");
            }
        } else if (isToken(Tag.ID) || isToken(Tag.AbreColchetes)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'void/bool/integer/string/double/[', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaCmd() {
        if (isToken(Tag.KW_compIf)) {
            Funcao9();
        } else if (isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln)) {
            Funcao10();
        } else if (isToken(Tag.ID)) {
            Funcao15();
        } else if (!(isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse))) {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ListaCmdLinha() {
        if (isToken(Tag.KW_compIf)) {
            Funcao9();
        } else if (isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln)) {
            Funcao10();
        } else if (isToken(Tag.ID)) {
            Funcao15();
        } else if (!(isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse))) {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Cmd() {
        if (isToken(Tag.KW_compIf)) {
            Funcao9();
        } else if (isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln)) {
            Funcao10();
        } else if (isToken(Tag.ID)) {
            Funcao15();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdIF() {
        if (isToken(Tag.KW_compIf)) {
            Funcao9();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdIFLinha() {
        if (isToken(Tag.KW_compEnd)) {
            if (!eat(Tag.KW_compEnd)) {
                erroSintatico("Esperado 'end', porem encontrado '" + token.getLexema() + "'");
            }
            if (!eat(Tag.PontoVirgula)) {
                erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.KW_compElse)) {
            if (!eat(Tag.KW_compElse)) {
                erroSintatico("Esperado 'else', porem encontrado '" + token.getLexema() + "'");
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
        } else if (isToken(Tag.KW_compReturn) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID) || isToken(Tag.KW_compIf)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdWhile() {
        if (isToken(Tag.KW_compWhile)) {
            Funcao11();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdWrite() {
        if (isToken(Tag.KW_compWrite)) {
            Funcao12();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdWriteln() {
        if (isToken(Tag.KW_compWriteln)) {
            Funcao13();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdAF() {
        if (isToken(Tag.ID)) {
            Funcao15();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdAFLinha() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao3();
            if (!eat(Tag.PontoVirgula)) {
                erroSintatico("Esperado ';', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.AbreColchetes)) {
            Funcao7();
        } else if (isToken(Tag.OpAtribuicao)) {
            Funcao14();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else/(/[/=', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void CmdAtribuiLinha() {
        if (isToken(Tag.AbreColchetes)) {
            Funcao7();
        } else if (isToken(Tag.OpAtribuicao)) {
            Funcao14();
        } else if (isToken(Tag.KW_compEnd) || isToken(Tag.KW_compReturn) || isToken(Tag.KW_compElse) || isToken(Tag.KW_compIf) || isToken(Tag.KW_compWhile) || isToken(Tag.KW_compWrite) || isToken(Tag.KW_compWriteln) || isToken(Tag.ID)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'if/while/write/writeln/end/return/else/[/=', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void FKExp() {
        if (isToken(Tag.Virgula)) {
            if (!eat(Tag.Virgula)) {
                erroSintatico("Esperado ',', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            FKExp();
        } else if (!isToken(Tag.FechaParenteses)) {
            //skip()
            erroSintatico("Esperado ',/)', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void QBExp() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função QBExp");
            }
        } else if (!isToken(Tag.FechaParenteses)) {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função Expressao");
            }
        } else if (isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/,/)/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void ExpressaoLinha() {
        if (isToken(Tag.KW_compAnd)) {
            if (!eat(Tag.KW_compAnd)) {
                erroSintatico("Esperado 'and', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao1();
            ExpressaoLinha();
        } else if (isToken(Tag.KW_compOr)) {
            if (!eat(Tag.KW_compOr)) {
                erroSintatico("Esperado 'or', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao1();
            ExpressaoLinha();
        } else if (!(isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao))) {
            //skip()
            erroSintatico("Esperado 'and/or/(/,/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao1() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função Expressao1");
            }
        } else if (isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/,/)/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao1Linha() {
        if (isToken(Tag.OpMaiorQue)) {
            if (!eat(Tag.OpMaiorQue)) {
                erroSintatico("Esperado '>', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpMaiorQueIgual)) {
            if (!eat(Tag.OpMaiorQueIgual)) {
                erroSintatico("Esperado '>=', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpMenorQue)) {
            if (!eat(Tag.OpMenorQue)) {
                erroSintatico("Esperado '<', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpMenorQueIgual)) {
            if (!eat(Tag.OpMenorQueIgual)) {
                erroSintatico("Esperado '<=', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpIgual)) {
            if (!eat(Tag.OpIgual)) {
                erroSintatico("Esperado '==', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpDiferente)) {
            if (!eat(Tag.OpDiferente)) {
                erroSintatico("Esperado '!=', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (!(isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr))) {
            //skip()
            erroSintatico("Esperado 'and/or/(/,/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao2() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função Expressao2");
            }
        } else if (isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao) || isToken(Tag.OpMaiorQue) || isToken(Tag.OpMaiorQueIgual) || isToken(Tag.OpMenorQue) || isToken(Tag.OpMenorQueIgual) || isToken(Tag.OpIgual) || isToken(Tag.OpDiferente)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/,/)/:/]/*/'/'/>/>=/</<=/==/!=', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao2Linha() {
        if (isToken(Tag.OpSoma)) {
            if (!eat(Tag.OpSoma)) {
                erroSintatico("Esperado '+', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (isToken(Tag.OpSubtracao)) {
            if (!eat(Tag.OpSubtracao)) {
                erroSintatico("Esperado '-', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (!(isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr) || isToken(Tag.OpMaiorQue) || isToken(Tag.OpMaiorQueIgual) || isToken(Tag.OpMenorQue) || isToken(Tag.OpMenorQueIgual) || isToken(Tag.OpIgual) || isToken(Tag.OpDiferente))) {
            //skip()
            erroSintatico("Esperado 'and/or/(/,/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao3() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função Expressao3");
            }
        } else if (isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr) || isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao) || isToken(Tag.OpMaiorQue) || isToken(Tag.OpMaiorQueIgual) || isToken(Tag.OpMenorQue) || isToken(Tag.OpMenorQueIgual) || isToken(Tag.OpIgual) || isToken(Tag.OpDiferente) || isToken(Tag.OpSoma) || isToken(Tag.OpSubtracao)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/,/)/:/]/*/'/'/>/>=/</<=/==/!=/+/-', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao3Linha() {
        if (isToken(Tag.OpMultiplicacao)) {
            if (!eat(Tag.OpMultiplicacao)) {
                erroSintatico("Esperado '*', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao4();
            Expressao3Linha();
        } else if (isToken(Tag.OpDivisao)) {
            if (!eat(Tag.OpDivisao)) {
                erroSintatico("Esperado '/', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao4();
            Expressao3Linha();
        } else if (!(isToken(Tag.PontoVirgula) || isToken(Tag.FechaParenteses) || isToken(Tag.Virgula) || isToken(Tag.FechaColchetes) || isToken(Tag.OpSoma) || isToken(Tag.OpSubtracao) || isToken(Tag.KW_compAnd) || isToken(Tag.KW_compOr) || isToken(Tag.OpMaiorQue) || isToken(Tag.OpMaiorQueIgual) || isToken(Tag.OpMenorQue) || isToken(Tag.OpMenorQueIgual) || isToken(Tag.OpIgual) || isToken(Tag.OpDiferente))) {
            //skip()
            erroSintatico("Esperado 'and/or/(/,/:/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao4() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao4();
        } else if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao)) {
            if (!Funcao16()) {
                erroSintatico("Ocorreu um erro sintatico na função Expressao4");
            }
        } else if (isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado '(/ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }
    }

    public void Expressao4Linha() {
        if (isToken(Tag.AbreParenteses)) {
            Funcao3();
        } else if (isToken(Tag.AbreColchetes)) {
            if (!eat(Tag.AbreColchetes)) {
                erroSintatico("Esperado '[', porem encontrado '" + token.getLexema() + "'");
            }
            Expressao();
            if (!eat(Tag.FechaColchetes)) {
                erroSintatico("Esperado ']', porem encontrado '" + token.getLexema() + "'");
            }
        } else if (!(isToken(Tag.OpMultiplicacao) || isToken(Tag.OpDivisao))) {
            //skip()
            erroSintatico("Esperado '(/[/]/*/'/'', porem encontrado '" + token.getLexema() + "'");
        }

    }

    public void OpUnario() {
        if (isToken(Tag.ID) || isToken(Tag.ConstInteger) || isToken(Tag.ConstDouble) || isToken(Tag.ConstString) || isToken(Tag.KW_compTrue) || isToken(Tag.KW_compFalse) || isToken(Tag.KW_compVector) || isToken(Tag.OpSubtracao) || isToken(Tag.OpUnarioNegacao) || isToken(Tag.AbreParenteses)) {
            //sync()
        } else {
            //skip()
            erroSintatico("Esperado 'ID/ConstInteger/ConstDouble/ConstString/true/false/vector/subtracao/!/*/'/'/(', porem encontrado '" + token.getLexema() + "'");
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
        Main();
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
        Retorno();
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
        CmdIFLinha();
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
        CmdAFLinha();
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
            TipoPrimitivo();
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
