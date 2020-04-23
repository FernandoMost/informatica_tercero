package MyPublishSubscribe;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartRateServer {
    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is), lineReader;
        String portNum, registryURL;
        
        try{     
            System.out.print("Introduzca el puerto para el registro RMI: ");
            portNum = (br.readLine()).trim();
            int RMIPortNum = Integer.parseInt(portNum);
            registryURL = "rmi://localhost:" + portNum + "/HeartRate";
            
            startRegistry(RMIPortNum);

            System.out.print("Introduzca el directorio del fichero a leer, siendo [ . = " + System.getProperty("user.dir") + "]: ");

            while(true) {
                try {
                    String filePath = (br.readLine()).trim();
                    lineReader = new BufferedReader(new FileReader(filePath));
                    System.out.println("Directorio válido!");
                    break;
                } catch(Exception e) {
                    System.out.print("Directorio no válido, vuelva a intertarlo: ");
                }
            }

            HeartRateServerImpl exportedObj = new HeartRateServerImpl(lineReader);
            System.setProperty("java.rmi.server.hostname","192.168.0.12");
            Naming.rebind(registryURL, exportedObj);

            System.out.println("\t[ " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " ] HeartRate Server LISTO y TRANSMITIENDO!");
        } catch (Exception re) {
            System.out.println("Exception in HeartRateServer.main: " + re);
        }
    }

    // This method starts a RMI registry on the local host, if it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException{
        try {
            Registry registry =  LocateRegistry.getRegistry(RMIPortNum);
            registry.list();  
            // This call will throw an exception if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            System.out.println("El registro no existe, creándolo...");
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
        }
    }
}
