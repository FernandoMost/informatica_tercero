import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File inputFile = new File("./src/ejDefinicion.txt");

        try {
            Automata a = new Automata(inputFile);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        }


    }
}
