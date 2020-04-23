import calculadora.Calculadora;
import analizadorTextos.AnalizadorTextos;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static final String MENU =
        "\n\n---------------PRACTICA SERVICIOS SOAP---------------\n" +
        "\t1. Calculadora\n" +
        "\t2. Analizador de Textos\n" +
        "\t3. Salir\n\n" +
        "Selección: ";

    private static final String MENU_CALCULADORA =
        "\n\n--------------- CALCULADORA ---------------\n" +
        "\t1. Suma de dos números.\n" +
        "\t2. Resta de dos números.\n" +
        "\t3. Multiplicación de dos números.\n" +
        "\t4. División de dos números.\n" +
        "\t5. Potencia de un número.\n" +
        "\t6. Raíz cuadrada de un número.\n" +
        "\t7. Logaritmo neperiano de un número.\n" +
        "\t8. Máximo de una lista de números.\n" +
        "\t9. Mínimo de una lista de números.\n" +
        "\t10. Media de una lista de números.\n" +
        "\t11. Mediana de una lista de números.\n" +
        "\t12. Moda de una lista de números.\n" +
        "\t13. Desviación típica de una lista de números.\n" +
        "\t14. Volver\n\n" +
        "Selección: ";

    private static final String MENU_ANALIZADOR_TEXTOS =
        "\n\n--------------- ANALIZADOR DE TEXTOS ---------------\n" +
        "\t1. Contar palabras.\n" +
        "\t2. Contar caracteres (incluyendo espacios, signos de puntuación, etc.)\n" +
        "\t3. Contar frases.\n" +
        "\t4. Número de veces que aparece una palabra.\n" +
        "\t5. Palabra más usada.\n" +
        "\t6. Palabra menos usada.\n" +
        "\t7. Reemplazar palabra.\n" +
        "\t8. Imprimir fichero.\n" +
        "\t9. Volver\n\n" +
        "Selección: ";

    public static void main(String[] args) throws IOException {
        URL calculadoraWsdlURL = new URL("http://localhost:8080/calculadora?wsdl");
        URL analizadorTextosWsdlURL = new URL("http://localhost:8080/analizadorTextos?wsdl");

        QName calculadora_SERVICE_NAME = new QName("http://calculadora/", "Calculadora");
        QName analizadorTextos_SERVICE_NAME = new QName("http://analizadorTextos/", "AnalizadorTextos");

        Service calculadoraService = Service.create(calculadoraWsdlURL, calculadora_SERVICE_NAME);
        Service analizadorTextosService = Service.create(analizadorTextosWsdlURL, analizadorTextos_SERVICE_NAME);

        Calculadora calculadora = calculadoraService.getPort(Calculadora.class);
        AnalizadorTextos analizadorTextos = analizadorTextosService.getPort(AnalizadorTextos.class);

        Scanner sn = new Scanner(System.in);
        boolean salir = false, volver; int opcion;
        BufferedReader lineReader;

        while (!salir) {

            System.out.print(MENU);

            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        volver = false;
                        int A, B; int[] array;

                        while (!volver) {
                            System.out.print(MENU_CALCULADORA);

                            try {
                                opcion = sn.nextInt();

                                switch (opcion) {
                                    case 1:
                                        System.out.print("Suma de dos números (A + B)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.print("\tB = ");
                                        B = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.suma(A, B));
                                        break;
                                    case 2:
                                        System.out.print("Resta de dos números (A - B)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.print("\tB = ");
                                        B = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.resta(A, B));
                                        break;
                                    case 3:
                                        System.out.print("Multiplicación de dos números (A * B)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.print("\tB = ");
                                        B = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.multiplicacion(A, B));
                                        break;
                                    case 4:
                                        System.out.print("División de dos números (A / B)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.print("\tB = ");
                                        B = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.division(A, B));
                                        break;
                                    case 5:
                                        System.out.print("Potencia de un número (A ^ B)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.print("\tB = ");
                                        B = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.potencia(A, B));
                                        break;
                                    case 6:
                                        System.out.print("Raíz cuadrada de un número √A\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.raizCuadrada(A));
                                        break;
                                    case 7:
                                        System.out.print("Logaritmo neperiano de un número log(A)\n\tA = ");
                                        A = sn.nextInt();

                                        System.out.println("Resultado = " + calculadora.logaritmoNeperiano(A));
                                        break;
                                    case 8:
                                        System.out.print("Máximo de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.maximo(array));
                                        break;
                                    case 9:
                                        System.out.print("Mínimo de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.minimo(array));
                                        break;
                                    case 10:
                                        System.out.print("Media de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.media(array));
                                        break;
                                    case 11:
                                        System.out.print("Mediana de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.mediana(array));
                                        break;
                                    case 12:
                                        System.out.print("Moda de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.moda(array));
                                        break;
                                    case 13:
                                        System.out.print("Desviación típica de una lista de números\n\t");

                                        array = scanListaNumeros(sn);

                                        System.out.println("Resultado = " + calculadora.desviacionTipica(array));
                                        break;
                                    case 14:
                                        volver = true;
                                        break;
                                    default:
                                        System.out.println("Solo números entre 1 y 14");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Debes insertar un número");
                                sn.next();
                            }
                        }

                        break;
                    case 2:
                        volver = false;


                        System.out.print("Introduzca el directorio del fichero a leer, siendo [ . = " + System.getProperty("user.dir") + "]: ");

                        while(true) {
                            try {
                                String filePath = (sn.next().trim());
                                lineReader = new BufferedReader(new FileReader(filePath));
                                System.out.println("Directorio válido!");
                                break;
                            } catch(Exception e) {
                                System.out.print("Directorio no válido, vuelva a intertarlo: ");
                            }
                        }

                        StringBuilder stringBuilder = new StringBuilder(); String linea, texto;

                        while ((linea = lineReader.readLine()) != null)
                            stringBuilder.append(linea).append("\n");

                        texto = stringBuilder.toString();

                        while (!volver) {
                            System.out.print(MENU_ANALIZADOR_TEXTOS);

                            try {
                                opcion = sn.nextInt();

                                switch (opcion) {
                                    case 1:
                                        System.out.println("Palabras totales = " + analizadorTextos.cuentaPalabrasTotales(texto));
                                        break;
                                    case 2:
                                        System.out.println("Caracteres totales = " + analizadorTextos.cuentaCaracteresTotales(texto));
                                        break;
                                    case 3:
                                        System.out.println("Frases totales = " + analizadorTextos.cuentaFrasesTotales(texto));
                                        break;
                                    case 4:
                                        String aBuscar;

                                        System.out.print("\tPalabra a buscar = ");
                                        aBuscar = sn.next();

                                        System.out.println("\n\tLa palabra \"" + aBuscar + "\" aparece " + analizadorTextos.cuentaPalabra(texto, aBuscar) + " veces.");
                                        break;
                                    case 5:
                                        System.out.println("Palabra más usada = " + analizadorTextos.palabraMasUsada(texto));
                                        break;
                                    case 6:
                                        System.out.println("Palabra menos usada = " + analizadorTextos.palabraMenosUsada(texto));
                                        break;
                                    case 7:
                                        String vieja, nueva;

                                        System.out.print("\tPalabra vieja = ");
                                        vieja = sn.next();

                                        System.out.print("\tPalabra nueva = ");
                                        nueva = sn.next();

                                        System.out.println("\n\n" + analizadorTextos.reemplazarPalabra(texto, vieja, nueva));
                                        break;
                                    case 8:
                                        System.out.println("\n\n" + texto + "\n\n");
                                        break;
                                    case 9:
                                        volver = true;
                                        break;
                                    default:
                                        System.out.println("Solo números entre 1 y 9");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Debes insertar un número");
                                sn.next();
                            }
                        }

                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 3");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }

    private static int[] scanListaNumeros(Scanner scanner) {
        System.out.print("Lista, separada por comas = ");
        String lista = scanner.next();
        String[] listaNumeros = lista.split(",");

        int[] listaNums = new int[listaNumeros.length];

        for (int i = 0; i < listaNumeros.length; i++)
            listaNums[i] = Integer.parseInt(listaNumeros[i]);

        return listaNums;
    }
}
