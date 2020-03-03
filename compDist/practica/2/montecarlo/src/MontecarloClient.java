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

            String message = h.sayHello("Donald Duck");
            System.out.println("HelloClient: " + message);
        } catch (Exception e) {
            System.out.println("Exception in HelloClient: " + e);
        }
    }
}