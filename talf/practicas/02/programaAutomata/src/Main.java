import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/ejDefinicion.txt");

        Automata a = new Automata(inputFile);
    }
}
