import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MontecarloServer  {
    public static void main(String[] args) {
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.print("RMIregistry port number: ");
            String portNumber = bufferedReader.readLine();

            String registryURL = "rmi://localhost:" + portNumber + "/montecarlo";

            startRegistry(Integer.parseInt(portNumber));

            MontecarloImpl exportedObject = new MontecarloImpl();
            Naming.rebind(registryURL, exportedObject);

            System.out.println("Server registered, registry currently contains:");
            listRegistry(registryURL);      // list names currently in the registry

            System.out.println("Montecarlo Server is READY.");
        } catch (Exception re) {
            System.out.println("Exception in MontecarloServer: " + re);
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
        for (String name : Naming.list(registryURL))
            System.out.println("\t"+name);
    }
}
