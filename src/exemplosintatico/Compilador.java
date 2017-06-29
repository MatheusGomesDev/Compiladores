package exemplosintatico;

/**
 *
 * @author gustavo
 */
public class Compilador {

    public static void main(String [] entrada) {

        Lexico lexer = new Lexico("C:\\Users\\piteu\\Desktop\\Trabalho compiladoes Ultimo 24062017\\TrabCompiladoresAAA1\\src\\exemplosintatico\\programa2.txt");
        Sintatico parser = new Sintatico(lexer);

        parser.Programa(); // producao inicial

        parser.fechaArquivos();
        System.out.println("Compilação de Programa Realizada!");
    }
}
