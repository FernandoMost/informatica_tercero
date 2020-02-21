package myMulticast;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

/**
 * This class represents the object server for a distributed
 * object of class Hello, which implements the remote interface
 * HelloInterface.
 * @author M. L. Liu
 */

public class HelloServer  {
    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        try{
            // obtaining port from user and setting it
            System.out.println("Enter the RMIregistry port number:");
            portNum = (br.readLine()).trim();
            int RMIPortNum = Integer.parseInt(portNum);
            startRegistry(RMIPortNum);

            // HelloImpl, which extends UnicastRemoteObject
            HelloImpl exportedObj = new HelloImpl();
            registryURL = "rmi://localhost:" + portNum + "/hello";
            Naming.rebind(registryURL, exportedObj);                // Rebinds the specified name to a new remote object. Any existing binding for the name is replaced.
            
            // some readyness + printing
            System.out.println("Server registered.  Registry currently contains:");
            // listing names currently in the registry
            listRegistry(registryURL); 
            System.out.println("Hello Server ready.");
        } catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        }
    }

    // This method starts a RMI registry on the local host, if it
    // does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );  // This call will throw an exception if the registry does not already exist
        } catch (RemoteException e) { 
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    }

    // This method lists the names registered with a Registry object
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String [ ] names = Naming.list(registryURL);
        for (int i=0; i < names.length; i++)
            System.out.println(names[i]);
    }
}
