package exemplosintatico;

/**
 *
 * @author gustavo
 */
public class Token {
	
    public final static int EOF		  				= 1;
    public final static int ID 		  				= 2;
    public final static int CONST_INT	  			= 3;
    public final static int OP_MAIOR_IGUAL	   = 4;
    public final static int OP_MAIOR	  			= 5;
    public final static int SMB_ABRE_PAREN	   = 6;
    public final static int SMB_FECHA_PAREN   	= 7;
    public final static int OP_MAIS          	= 8;
    public final static int OP_VEZES         	= 9;
    public final static int SMB_PONTO_VIRGULA 	= 10;

    public final static int KW_PUBLIC        		= 50;
    public final static int KW_CLASS	  				= 51;
    public final static int KW_SYSOUTDISPLN  		= 52;
    public final static int KW_END           		= 53;

    private String lexema;
    private int classe;
    private int linha;
    private int coluna;

    public Token(int classe, String lexema, int linha, int coluna) {

        this.classe = classe;
        this.lexema = lexema;
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getClasse() {

        return classe;
    }

    public void setClasse(int classe) {

        this.classe = classe;
    }

    public String getLexema() {

        return lexema;
    }

    public void setLexema(String lexema) {

        this.lexema = lexema;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    @Override
    public String toString() {
        return lexema + " (linha " + linha + " e coluna " + coluna + ")\n";
    }
}
