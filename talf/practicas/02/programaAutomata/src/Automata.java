import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Automata {
    private ArrayList<Estado> estados;
    private Estado estadoInicial;
    private HashSet<Estado> estadosFinales;

    private ArrayList<ArrayList<HashSet<Estado>>> matrizTransiciones;

    private HashSet<Character> alfabeto;

    /* ----------------------------------------------------------------- */

    public Automata(File archivo) throws Exception {
        /*  Función que interpreta en archivo de entrada */

        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        int i, numEstados = 0, numEstadosFinales = 0, numElemsAlfabeto = 0;
        String[] lineaSeparada = null;
        String linea;

        // ESTADOS

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");

        if (lineaSeparada == null) throw new Exception("Problemas al leer linea en el fichero");

        if (lineaSeparada[0].charAt(0) == '#') {
            numEstados = Integer.parseInt(lineaSeparada[0].replace("#", ""));

            if ((lineaSeparada.length-1) != numEstados)
                throw new Exception("#X no coincide con el numero de estados");
        } else {
            throw new Exception("No se incluye # en la linea de estados");
        }

        this.estados = new ArrayList<>();
        String nombreEstado;

        for (i = 1; i < numEstados; i++) {
            Estado e = new Estado(lineaSeparada[i]);

            this.estados.add(i-1, e);

            if (i == 1) this.estadoInicial = e;
        }

        // ESTADOS FINALES

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");

        if (lineaSeparada[0].charAt(0) == '#') {
            numEstadosFinales = Integer.parseInt(lineaSeparada[0].replace("#", ""));

            if ((lineaSeparada.length-1) != numEstadosFinales)
                throw new Exception("#X no coincide con el numero de estados finales");

            if (numEstadosFinales > numEstados)
                throw new Exception("Hay más estados finales que estados posibles");
        } else {
            throw new Exception("No se incluye # en la linea de estados finales");
        }

        this.estadosFinales = new HashSet<>();

        for (Estado e : this.estados)
            for (i = 1; i < numEstadosFinales; i++)
                if (lineaSeparada[i].equals(e.getNombre())) {
                    this.estadosFinales.add(e);
                    break;
                }

        // ALFABETO

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");

        if (lineaSeparada[0].charAt(0) == '#') {
            numElemsAlfabeto = Integer.parseInt(lineaSeparada[0].replace("#", ""));

            if ((lineaSeparada.length-1) != numElemsAlfabeto)
                throw new Exception("#X no coincide con el numero de caracteres en el alfabeto");
        } else {
            throw new Exception("No se incluye # en la linea del alfabeto");
        }

        this.alfabeto = new HashSet<>();

        for (i = 1; i < numElemsAlfabeto; i++) {
            alfabeto.add(lineaSeparada[i].charAt(0));
        }

        alfabeto.add('λ'); numElemsAlfabeto++;

        // TABLA DE TRANSICIONES

        linea = reader.readLine(); // --TABLA DE TRANSICIONES--

        this.matrizTransiciones = new ArrayList<>();

        String[] celdaTabla = null;

        for (i = 0; i < numEstados; i++) {
            linea = reader.readLine();
            this.matrizTransiciones.add(i, new ArrayList<>());

            if (linea != null) lineaSeparada = linea.split("#");

            for (int j = 0; j < numElemsAlfabeto; j++) {
                HashSet<Estado> celda = new HashSet<>();

                this.matrizTransiciones.get(i).add(j, celda);

                if (lineaSeparada[j] != null) {
                    celdaTabla = lineaSeparada[j].split(" ");
                }

                if (celdaTabla != null)
                    for (String st : celdaTabla)
                        for (Estado e : this.estados)
                            if (st.equals(e.getNombre()))
                                celda.add(e);
            }
        }
    }

    // ----------------------------------------------------------------------------------------

    public boolean solve(String cadena) throws Exception {
        for (int i = 0; i < cadena.length(); i++)
            if (!this.alfabeto.contains(cadena.charAt(i)))
                throw new Exception("La cadena contiene un caracter que no pertenece a alfabeto");





        return true;
    }

    // ----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder automata = new StringBuilder("Estados: ");
        for (Estado e : this.estados) automata.append(e.getNombre()).append(" ");

        automata.append("\nFinales: ");
        for (Estado e : this.estadosFinales) automata.append(e.getNombre()).append(" ");

        automata.append("\nAlfabeto: ");
        for (Character a : this.alfabeto) automata.append(a).append(" ");

        automata.append("\nTabla de transiciones:\n");
        for (int i = 0; i < this.estados.size(); i++) {
            for (int j = 0; j < this.alfabeto.size(); j++) {
                for (Estado e : this.matrizTransiciones.get(i).get(j)) automata.append(e.getNombre()).append(" ");
                automata.append("| ");
            }
            automata.append("\n");
        }

        return automata.toString();
    }
}
