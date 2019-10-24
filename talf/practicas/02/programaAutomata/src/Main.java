import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 1) throw new Exception("Falta el fichero con la definicion del autómata!");

            File inputFile = new File(args[0]);

            boolean salir = false;
            Scanner scanner = new Scanner(System.in);

            Automata a = new Automata(inputFile);

            System.out.println(a.toString());

            while (!salir) {
                System.out.print(
                    "\n1. Resolver cadena" +
                    "\n2. Salir" +
                    "\n\n > ");

                switch (scanner.nextLine().replace(" ", "")) {
                    case "1":
                        System.out.print("\nCadena: ");
                        String cadenaInput = scanner.nextLine();

                        boolean resultado = a.solve(cadenaInput);

                        if (resultado)
                            System.out.println("\u001B[32m" + "\nLA CADENA \"" + cadenaInput + "\" ES ACEPTADA." + "\u001B[0m");
                        else
                            System.out.println("\u001B[31m" + "\nLA CADENA \"" + cadenaInput + "\" ES RECHAZADA." + "\u001B[0m");

                        break;
                    case "2":
                        System.out.println("\nFin del programa");
                        salir = true;
                        break;
                    default:
                        System.out.println("\nOpción incorrecta");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

class Estado {
    private String nombre;

    Estado(String nombre) {
        this.nombre = nombre;
    }

    String getNombre() {
        return nombre;
    }
}

// ───────────────────────────────────────────────────────────────────────────────────────────

class Automata {
    private ArrayList<Estado> estados;
    private Estado            estadoInicial;
    private HashSet<Estado>   estadosFinales;

    private ArrayList<ArrayList<HashSet<Estado>>> matrizTransiciones;

    private ArrayList<Character> alfabeto;

    // ──────────────────────────────────────────────────────────────────────────

    Automata(File archivo) throws Exception {
        /*  Función que interpreta en archivo de entrada */

        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        int i, numEstados, numEstadosFinales, numElemsAlfabeto;
        String[] lineaSeparada = null;
        String linea;

        // ESTADOS ─────────────────────────────────────────────────

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");        // Separar los elementos separados por espacios

        if (lineaSeparada == null) throw new Exception("Problemas al leer linea en el fichero");

        if (lineaSeparada[0].charAt(0) == '#') {
            numEstados = Integer.parseInt(lineaSeparada[0].replace("#", ""));   // string -> int

            if ((lineaSeparada.length-1) != numEstados)
                throw new Exception("#X no coincide con el numero de estados");
        } else
            throw new Exception("No se incluye # en la linea de estados");

        this.estados = new ArrayList<>();

        for (i = 0; i < numEstados; i++) {
            Estado e = new Estado(lineaSeparada[i+1]);  // Se crean los estados según su nombre

            this.estados.add(i, e);                     // Se añaden al automata

            if (i == 0) this.estadoInicial = e;         // el primero se marca como el inicial
        }

        // ESTADOS FINALES ─────────────────────────────────────────

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");

        if (lineaSeparada[0].charAt(0) == '#') {
            numEstadosFinales = Integer.parseInt(lineaSeparada[0].replace("#", ""));

            if ((lineaSeparada.length-1) != numEstadosFinales)
                throw new Exception("#X no coincide con el numero de estados finales");

            if (numEstadosFinales > numEstados)
                throw new Exception("Hay más estados finales que estados posibles");
        } else
            throw new Exception("No se incluye # en la linea de estados finales");

        this.estadosFinales = new HashSet<>();

        for (Estado e : this.estados)                               // Iterando por los estados del autómata
            for (i = 0; i < numEstadosFinales; i++)                 // Iterando por los estados en la línea
                if (lineaSeparada[i+1].equals(e.getNombre())) {     // Si el nombre del estado está en la línea
                    this.estadosFinales.add(e);                     // Marcado en los finales del automata
                    break;
                }

        // ALFABETO ────────────────────────────────────────────────

        linea = reader.readLine();

        if (linea != null) lineaSeparada = linea.split(" ");

        if (lineaSeparada[0].charAt(0) == '#') {
            numElemsAlfabeto = Integer.parseInt(lineaSeparada[0].replace("#", ""));

            if ((lineaSeparada.length-1) != numElemsAlfabeto)
                throw new Exception("#X no coincide con el numero de caracteres en el alfabeto");
        } else
            throw new Exception("No se incluye # en la linea del alfabeto");

        this.alfabeto = new ArrayList<>();

        for (i = 0; i < numElemsAlfabeto; i++)
            alfabeto.add(lineaSeparada[i+1].charAt(0)); // Se añaden los caracteres al alfabeto del automata

        alfabeto.add('λ'); numElemsAlfabeto++;          // Además la cadena vacía

        // TABLA DE TRANSICIONES ───────────────────────────────────

        linea = reader.readLine(); // --TABLA DE TRANSICIONES--

        this.matrizTransiciones = new ArrayList<>();

        String[] celdaTabla = null;

        for (i = 0; i < numEstados; i++) {                          // Filas como núm de estados
            linea = reader.readLine();
            this.matrizTransiciones.add(i, new ArrayList<>());      // Se crea la fila en la matriz

            if (linea != null) lineaSeparada = linea.split("#");    // Separa las columnas de la fila = celda

            for (int j = 0; j < numElemsAlfabeto; j++) {            // Columnas como elems. en el alfabeto
                HashSet<Estado> celda = new HashSet<>();

                this.matrizTransiciones.get(i).add(j, celda);       // Se crea la celda en la fila

                if (lineaSeparada[j] != null) celdaTabla = lineaSeparada[j].split(" ");

                if (celdaTabla != null)
                    for (String st : celdaTabla)            // Iterando por los estados de la celda en la línea del fichero
                        for (Estado e : this.estados)       // Iterando por los estados del automata
                            if (st.equals(e.getNombre()))   // Si coincide
                                celda.add(e);               // se añade el estado a la celda en la tabla
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────

    private HashSet<Estado> transicion(HashSet<Estado> estadosActuales, Character entrada) {
        HashSet<Estado> nuevosEstados = new HashSet<>();
        int columna = this.alfabeto.indexOf(entrada);

        if (this.alfabeto.contains(entrada)) {
            for (Estado e : estadosActuales ) {                                     // Iterando por los estados
                int fila = this.estados.indexOf(e);                                 // Fila en la tabla

                System.out.print("Estado " + e.getNombre() + " -> ");               // Se imprime el estado y los que se transiciona a partir de este y la entrada

                for (Estado prox : this.matrizTransiciones.get(fila).get(columna))  // iterando por los estados en la celda
                    for (Estado estCla : this.clausura(prox)) {                     // Se pasa a los estados de su clausura
                        System.out.print(estCla.getNombre() + " ");
                        nuevosEstados.add(estCla);
                    }

                System.out.println();
            }

            return nuevosEstados;       // El HashSet no permite elementos repetidos, obtenemos en nuevo conjunto de estados
        }

        // Si el caracter no está en el alfabeto se devuelve un conjunto vacío
        return nuevosEstados;
    }

    private HashSet<Estado> clausura(Estado e) {
        HashSet<Estado> set = new HashSet<>();

        set.add(e);         // El propio estado

        // Iterando por los estados a los que se transiciona con la cadena vacia
        for (Estado salidaCadenaVacia : this.matrizTransiciones.get(this.estados.indexOf(e)).get(this.alfabeto.indexOf('λ')))
            set.addAll(this.clausura(salidaCadenaVacia));       // Se añade su clausura (función recursiva)

        return set;
    }

    private boolean aceptable(HashSet<Estado> estados) {
        /* Determina si un conjunto se estados es aceptable */

        for (Estado e : estados)
            if (this.estadosFinales.contains(e))
                return true;

        return false;
    }

    boolean solve(String cadena) {
        HashSet<Estado> estadosActuales = new HashSet<>();

        estadosActuales.add(this.estadoInicial);        // Comienza en el estado inicial

        for (int i = 0; i < cadena.length(); i++) {     // iterando por los caracteres de la cadena
            System.out.println("\nPaso " + (i+1));

            System.out.print("Estados actuales: ");
            for (Estado e : estadosActuales) System.out.print(e.getNombre() + " ");

            System.out.println("\nEntrada: " + cadena.charAt(i) + "\n");

            // Se hace la transicion según la entrada, nuevos estados actuales
            estadosActuales = transicion(estadosActuales, cadena.charAt(i));

            System.out.print("\nEstados actuales: ");
            for (Estado e : estadosActuales) System.out.print(e.getNombre() + " ");

            System.out.println("\nAceptable: " + this.aceptable(estadosActuales));

            System.out.println("\n─────────────────────────────");
        }


        return this.aceptable(estadosActuales);
    }

    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public String toString() {  // Imprime todas las características del autómata
        StringBuilder print = new StringBuilder(String.format("%10s : ", "Estados")), celda;
        for (Estado e : this.estados) print.append(e.getNombre()).append(" ");

        print.append(String.format("\n%10s : ", "Finales"));
        for (Estado e : this.estadosFinales) print.append(e.getNombre()).append(" ");

        print.append(String.format("\n%10s : ", "Alfabeto"));
        for (Character e : this.alfabeto) print.append(e).append(" ");

        print.append("\n\nTabla de transiciones:\n\n");

        print.append("       │");
        for (Character c : this.alfabeto) print.append(String.format("%-8s│ ", c));

        print.append("\n──────────");
        for (int i = 0; i < this.alfabeto.size(); i++) print.append("──────────");

        for (int i = 0; i < this.estados.size(); i++) {
            print.append(String.format("\n%6s │", this.estados.get(i).getNombre()));

            for (int j = 0; j < this.alfabeto.size(); j++) {
                celda = new StringBuilder();

                for (Estado e : this.matrizTransiciones.get(i).get(j))
                    celda.append(e.getNombre()).append(" ");

                print.append(String.format("%8s│ ", celda.toString()));
            }
        }

        return print.toString();
    }
}
