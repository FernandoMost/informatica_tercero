import java.rmi.Naming;

public class MontecarloClient {

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

            MontecarloInterface h = (MontecarloInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed " );

            int m = h.doTheMonthecarloMethod(1000000);
            double piCuartos = (double) m / 1000000;


            System.out.println("MontecarloClient: " + piCuartos*4.0);
        } catch (Exception e) {
            System.out.println("Exception in HelloClient: " + e);
        }
    }
}