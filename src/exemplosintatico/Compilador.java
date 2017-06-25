package exemplosintatico;

/**
 *
 * @author gustavo
 */
public class Compilador {

    public static void main(String [] entrada) {

        Lexico lexer = new Lexico("./programa2.txt");
        Sintatico parser = new Sintatico(lexer);

        parser.Programa(); // producao inicial

        parser.fechaArquivos();
        System.out.println("Compilação de Programa Realizada!");
    }
}
