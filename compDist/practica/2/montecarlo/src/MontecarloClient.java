import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.Naming;

public class MontecarloClient {
    // Array de tamaño {núm de hilos}, contiene las iteraciones de x, y aleatorios que cumplen la condición del método de montecarlo
    private static int[] cumplenCondicion;

    // Clase hilo que ejecuta el método montecarlo a través de la interfaz
    public static class MontecarloClientThread extends Thread {
        MontecarloInterface montecarloInterface;
        int iteraciones;

        MontecarloClientThread(MontecarloInterface montecarloInterface, int iteraciones) {
            this.montecarloInterface = montecarloInterface;
            this.iteraciones = iteraciones;
        }

        @Override
        public void run() {
            try {
                cumplenCondicion[Integer.parseInt(this.getName())] = montecarloInterface.doTheMonthecarloMethod(iteraciones);
            } catch (Exception e) {
                System.out.println("Exception in MontecarloClient: " + e);
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

            try {
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

            cumplenCondicion = new int[num_hilos];
            MontecarloClientThread[] hilos = new MontecarloClientThread[num_hilos];

            for (int i = 0; i < num_hilos; i++) {
                hilos[i] = new MontecarloClientThread(anInterface, num_iteraciones);
                hilos[i].setName(String.valueOf(i));
            }

            int totalCumplenCondicion = 0;
            double totalIteraciones = num_hilos*num_iteraciones;

            System.out.println("Starting the multithreading montercarlo method...");

            long startTime = System.nanoTime();     // --------------------------- INICIO ------------------------------

            for (int i = 0; i < num_hilos; i++)
                hilos[i].start();

            for (int i = 0; i < num_hilos; i++)
                hilos[i].join();

            for (int valor : cumplenCondicion)
                totalCumplenCondicion += valor;

            double piCuartos = (double) totalCumplenCondicion / totalIteraciones;

            long endTime = System.nanoTime();       // ----------------------------- FIN -------------------------------

            System.out.println("Multithreading montercarlo method just finished!");
            double seconds = (double)(endTime - startTime) / 1_000_000_000.0;

            System.out.println(
                    "\nNumber of threads\t\t" +   num_hilos +
                    "\nIterations per thread\t" + num_iteraciones +
                    "\nTotal iterations\t\t" +    totalIteraciones +
                    "\nRatio obtained\t\t\t" +    piCuartos +
                    "\nPi obtained\t\t\t\t" +     (piCuartos*4.0) +
                    "\nACTUAL Pi\t\t\t\t" +       "3.14159265358979323846264" +
                    "\nExecution time (s)\t\t" +  seconds);
        } catch (Exception e) {
            System.out.println("Exception in MontecarloClient: " + e);
        }
    }
}