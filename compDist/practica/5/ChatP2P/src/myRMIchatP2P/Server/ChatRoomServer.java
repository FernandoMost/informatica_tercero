package myRMIchatP2P.Server;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatRoomServer {
    public static void main(String[] args) {
        String registryURL = "rmi://localhost:1099/ChatRoom";
        
        try{
            startRegistry(1099);

            /*
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
            */

            ChatRoomServerImpl exportedObj = new ChatRoomServerImpl();
            System.setProperty("java.rmi.server.hostname","192.168.0.12");
            Naming.rebind(registryURL, exportedObj);

            System.out.println("\t[ " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " ] Chat Room Server LISTO!");
        } catch (Exception re) {
            System.out.println("Exception in ChatRoomServer.main: " + re);
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
