import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.Naming;

public class MontecarloClient {
    private static int[] laCumplen;

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
                laCumplen[Integer.parseInt(this.getName())] = montecarloInterface.doTheMonthecarloMethod(iteraciones);
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

            laCumplen = new int[num_hilos];
            MontecarloClientThread[] hilos = new MontecarloClientThread[num_hilos];

            long startTime = System.nanoTime();

            for (int i = 0; i < num_hilos; i++) {
                hilos[i] = new MontecarloClientThread(anInterface, num_iteraciones);
                hilos[i].setName(String.valueOf(i));
                hilos[i].start();
            }

            for (int i = 0; i < num_hilos; i++)
                hilos[i].join();

            int totalLaCumplen = 0;
            double totalIteraciones = num_hilos*num_iteraciones;

            for (int valor : laCumplen)
                totalLaCumplen += valor;

            System.out.println("la cumplen = " + totalLaCumplen);
            System.out.println("iteraciones totales = " + totalIteraciones);

            double piCuartos = (double)totalLaCumplen / totalIteraciones;

            System.out.println("Pi = " + piCuartos*4.0);

            long endTime = System.nanoTime();

            double seconds = (double)(endTime - startTime) / 1_000_000_000.0;
            System.out.println("Execution time in seconds  : " + seconds);
        } catch (Exception e) {
            System.out.println("Exception in MontecarloClient: " + e);
        }
    }
}