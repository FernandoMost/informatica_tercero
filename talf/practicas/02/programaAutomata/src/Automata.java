import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Automata {
    private Estado[] estados;
    private Estado estadoInicial;
    private HashSet<Estado> estadosFinales;

    private Estado[][] matrizTransiciones;

    private HashSet<String> alfabeto;

    /* ----------------------------------------------------------------- */

    public Automata(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }

    }
}
