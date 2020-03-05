import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MontecarloServer  {
    public static void main(String[] args) {
        //InputStreamReader is = new InputStreamReader(System.in);
        //BufferedReader br = new BufferedReader(is);

        String portNumber, registryURL;
        try{
            //System.out.println("Enter the RMIregistry port number:");
            //portNumber = (br.readLine()).trim();

            portNumber = "1099";
            int RMIPortNumber = Integer.parseInt(portNumber);

            startRegistry(RMIPortNumber);
            registryURL = "rmi://localhost:" + portNumber + "/montecarlo";

            MontecarloImpl exportedObject = new MontecarloImpl();
            Naming.rebind(registryURL, exportedObject);

            System.out.println("Server registered, registry currently contains:");
            listRegistry(registryURL);      // list names currently in the registry
            System.out.println("Montecarlo Server is READY.");
        } catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        }
    }

    // This method starts a RMI registry on the local host, if it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );               // Will throw an exception if it doesn't already exist
        } catch (RemoteException e) {       // No valid registry at that port.
            System.out.println("RMI registry does not exists at port " + RMIPortNum);
            registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    }

    // This method lists the names registered with a Registry object
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        String[] names = Naming.list(registryURL);
        for (String name : names) System.out.println("\t"+name);
    }
}
