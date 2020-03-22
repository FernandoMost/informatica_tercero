import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class RemoteIntegralServer {
	public static void main(String[] args) {
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String portNum, registryURL;
		try {
                        System.setSecurityManager(new RMISecurityManager());
			System.out.println("Enter the RMIregistry port number:");
			portNum = (br.readLine()).trim();
			int RMIPortNum = Integer.parseInt(portNum);
			startRegistry(RMIPortNum);
			RemoteIntegralImpl integral = new RemoteIntegralImpl();
			registryURL = "rmi://localhost:" + portNum + "/RemoteIntegral";
			Naming.rebind(registryURL, integral);
		}
		catch (Exception re) {
			System.out.println("Exception in HelloServer.main: " + re);
		} // end catch
	}


	private static void startRegistry(int RMIPortNum)
		throws RemoteException{
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list( );  // This call will throw an exception
                            // if the registry does not already exist
		}
		catch (RemoteException e) { 
         // No valid registry at that port.
/**/			System.out.println
/**/				("RMI registry cannot be located at port " 
/**/				+ RMIPortNum);
			Registry registry = 
				LocateRegistry.createRegistry(RMIPortNum);
/**/			System.out.println(
/**/				"RMI registry created at port " + RMIPortNum);
		}
	} // end startRegistry
}
