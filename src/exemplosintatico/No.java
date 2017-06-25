package exemplosintatico;

import java.util.ArrayList;
/**
 *
 * @author gustavo
 */
public class No {

    // Atributos da classe No
    private Token pai;
    private final ArrayList<No> listaFilhos;
    private static int espaco = 0;

    // Atributos semânticos
    public int tipo = 0;
    public String code = "";
    public static int endereco = 0;

    // Constantes para tipos
    public static Integer TIPO_VAZIO	= 0;
    public static Integer TIPO_INT		= 1;
    public static Integer TIPO_STRING	= 2;
    public static Integer TIPO_ERRO		= 3;

    public No(Token token) {
        this.pai = token;
        this.listaFilhos = new ArrayList<>();
    }

    public void setPai(Token token) {
        this.pai = token;
    }

    public ArrayList<No> getFilhos() {
        return listaFilhos;
    }

    public void addTodos(ArrayList<No> listaFilhos) {
        this.listaFilhos.addAll(listaFilhos);
    }

    public void addFilho(No filho) {
        this.listaFilhos.add(filho);
    }

    public void imprimeConteudo() {
        if (this.pai != null) {
            for (int i = 0; i < espaco; i++) {
                System.out.print(".   ");
            }
            System.out.print(this.pai.toString());
            espaco++;
        }
        for (No filho : listaFilhos) {
            filho.imprimeConteudo();
        }
        if (this.pai != null) {
            espaco--;
        }
    }
}
