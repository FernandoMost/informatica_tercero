import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Automata {
    private ArrayList<Estado> estados;
    private Estado estadoInicial;
    private HashSet<Estado> estadosFinales;

    private Estado[][] matrizTransiciones;

    private HashSet<String> alfabeto;

    /* ----------------------------------------------------------------- */

    public Automata(File archivo) throws IOException {
        /*  Función que interpreta en archivo de entrada */

        BufferedReader reader = new BufferedReader(new FileReader(archivo));

        String linea;

        // ESTADOS

        /*
         *   1. Sacar el número de estados, debe de llevar #
         *   2. Separar los nombres de los estados, antes comprobar que la cantidad coincide
         */

        linea = reader.readLine();

        String[] lineaEstados;

        if (linea != null) {
            lineaEstados = linea.split(" ");
        }

        // if (lineaEstados == null)

        if (lineaEstados[0].charAt(0) == '#') {
            int numEstados = Integer.parseInt(lineaEstados[0].replace("#", ""));

            if ((lineaEstados.length-1) != numEstados) {
                //fin
            }
        } else {
            //fin
        }

        this.estados = new ArrayList<>();

        for (int i = 1; i < lineaEstados.length; i++) {
            this.estados.add(i-1, new Estado(lineaEstados[i]));
        }

        // ESTADOS FINALES

        /*
        *   3. Sacar el número de estados finales, debe de llevar # y ser menor o igual que el total
        *   4. Separar los nombres de los estados finales, antes comprobar que la cantidad coincide y que existen
        */

        // ALFABETO

        /*
        *   5. Sacar el número del alfabeto, debe de llevar #
        *   6. Separar el alfabeto, antes comprobar que la cantidad coincide + cadena vacía
        */

        // TABLA DE TRANSICIONES

        /*
        *   7. --TABLA DE TRANSICIONES--
        *   8. for(cada linea como estados)
        *          for(cada # como alfabeto)
        *              separar los estados --> hashset de Estados
        */


        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }



    }
}
