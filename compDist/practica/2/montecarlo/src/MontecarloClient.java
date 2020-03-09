import java.rmi.Naming;

public class MontecarloClient {
    static int[] laCumplen;

    public static class MontecarloClientThread extends Thread {
        MontecarloInterface montecarloInterface;
        int iteraciones;

        MontecarloClientThread(MontecarloInterface montecarloInterface, int iteraciones) {
            this.montecarloInterface = montecarloInterface;
            this.iteraciones = iteraciones;
        }

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

            final int NUM_HILOS = 4;
            final int NUM_ITERACIONES = 1000000;

            laCumplen = new int[NUM_HILOS];

            MontecarloInterface anInterface = (MontecarloInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed " );

            for (int i = 0; i < NUM_HILOS; i++) {
                MontecarloClientThread hilo = new MontecarloClientThread(anInterface, NUM_ITERACIONES);
                hilo.setName(String.valueOf(i));
                hilo.start();
            }


        } catch (Exception e) {
            System.out.println("Exception in MontecarloClient: " + e);
        }
    }
}