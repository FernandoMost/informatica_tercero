import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File inputFile = new File("./src/02");

        try {
            String inputString = "001101";

            Automata a = new Automata(inputFile);

            System.out.println(a.toString());

            System.out.println(a.solve(inputString));

        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}
