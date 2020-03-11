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
            //InputStreamReader is = new InputStreamReader(System.in);
            //BufferedReader br = new BufferedReader(is);
            //System.out.println("Enter the RMIRegistry host namer:");
            //hostName = br.readLine();
            //System.out.println("Enter the RMIregistry port number:");
            //String portNum = br.readLine();

            String hostName = "localhost";
            String portNum = "1099";
            String registryURL = "rmi://" + hostName+ ":" + portNum + "/montecarlo";

            final int NUM_HILOS = 8;
            final int NUM_ITERACIONES = 50000000;

            laCumplen = new int[NUM_HILOS];
            MontecarloClientThread[] hilos = new MontecarloClientThread[NUM_HILOS];

            MontecarloInterface anInterface = (MontecarloInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed " );

            for (int i = 0; i < NUM_HILOS; i++) {
                hilos[i] = new MontecarloClientThread(anInterface, NUM_ITERACIONES);
                hilos[i].setName(String.valueOf(i));
                hilos[i].start();
            }

            for (int i = 0; i < NUM_HILOS; i++)
                hilos[i].join();

            int totalLaCumplen = 0;
            double totalIteraciones = NUM_HILOS*NUM_ITERACIONES;

            for (int valor : laCumplen)
                totalLaCumplen += valor;

            System.out.println("la cumplen = " + totalLaCumplen);
            System.out.println("iteraciones totales = " + totalIteraciones);

            double piCuartos = (double)totalLaCumplen / totalIteraciones;

            System.out.println("Pi = " + piCuartos*4.0);
        } catch (Exception e) {
            System.out.println("Exception in MontecarloClient: " + e);
        }
    }
}