import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.Naming;

public class MontecarloClient {
    // Array de tamaño {núm de hilos}, contiene las iteraciones de x, y aleatorios que cumplen la condición del método de montecarlo
    private static int[] cumplenCondicion;

    // Clase hilo que ejecuta el método montecarlo a través de la interfaz
    public static class MontecarloClientThread extends Thread {
        private MontecarloInterface montecarloInterface;
        private int iteraciones;

        MontecarloClientThread(MontecarloInterface montecarloInterface, int iteraciones) {
            this.montecarloInterface = montecarloInterface;
            this.iteraciones = iteraciones;
        }

        @Override
        public void run() {
            try {
                int id = Integer.parseInt(this.getName());
                cumplenCondicion[id] = this.montecarloInterface.doTheMonthecarloMethod(this.iteraciones);
            } catch (Exception e) {
                System.out.println("Exception in MontecarloClientThread: " + e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String hostName, portNumber, threadNumber, iterationsNumber;

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.print("RMIRegistry host name: ");
            hostName = bufferedReader.readLine();

            System.out.print("RMIregistry port number: ");
            portNumber = bufferedReader.readLine();

            String registryURL = "rmi://" + hostName+ ":" + portNumber + "/montecarlo";
            MontecarloInterface anInterface = null;

            try {           // Look up en el URL para comprobar que sea válida
                anInterface = (MontecarloInterface) Naming.lookup(registryURL);
                System.out.println("Lookup completed!");
            } catch (ConnectException e) {
                System.out.println("Conection refused: " + registryURL + "\tEnding the process...");
                System.exit(-1);
            }

            System.out.print("Number of threads: ");
            threadNumber = bufferedReader.readLine();

            System.out.print("Number of iterations per thread: ");
            iterationsNumber = bufferedReader.readLine();

            int num_hilos = Integer.parseInt(threadNumber), num_iteraciones = Integer.parseInt(iterationsNumber);
            double totalIteraciones = num_hilos*num_iteraciones;

            MontecarloClientThread[] hilos = new MontecarloClientThread[num_hilos];     // Array con los hilos totales

            for (int i = 0; i < num_hilos; i++) {       // Creación y setup de los hilos
                hilos[i] = new MontecarloClientThread(anInterface, num_iteraciones);
                hilos[i].setName(String.valueOf(i));
            }

            cumplenCondicion = new int[num_hilos];      // Array con los valores de iteraciones que cumplen la condición, por cada hilo
            int totalCumplenCondicion = 0;

            System.out.println("Starting the multithreaded montercarlo method...");

            long startTime = System.nanoTime();     // --------------------------- INICIO ------------------------------

            for (int i = 0; i < num_hilos; i++)     // comienzo de los hilos
                hilos[i].start();

            for (int i = 0; i < num_hilos; i++)     // espera a que acaben los hilos, para coordinar el programa
                hilos[i].join();

            for (int valor : cumplenCondicion)
                totalCumplenCondicion += valor;     // suma de los resultados de los hilos

            double piCuartos = (double) totalCumplenCondicion / totalIteraciones;       // obtención del rateo pi/4 según el método montecarlo

            long endTime = System.nanoTime();       // ----------------------------- FIN -------------------------------

            System.out.println("Multithreaded montercarlo method just finished!");
            double seconds = (double)(endTime - startTime) / 1_000_000_000.0;           // nanosegundos -> segundos

            System.out.println(
                    "\nNumber of threads\t\t" +   num_hilos +
                    "\nIterations per thread\t\t" + num_iteraciones +
                    "\nTotal iterations\t\t" +    totalIteraciones +
                    "\nRatio obtained\t\t\t" +    piCuartos +
                    "\nPi obtained\t\t\t" +     (piCuartos*4.0) +
                    "\nACTUAL Pi\t\t\t" +       "3.1415926535897932" +
                    "\nExecution time (s)\t\t" +  seconds);
        } catch (Exception e) {
            System.out.println("Exception in MontecarloClient: " + e);
        }
    }
}