package MyPublishSubscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class HeartRateServerImpl extends UnicastRemoteObject implements HeartRateServerInterface {
    private Vector clientList;
    private BufferedReader fileBuffer;
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public HeartRateServerImpl(BufferedReader bufferedReader) throws RemoteException {
        super();
        clientList = new Vector();
        fileBuffer = bufferedReader;

        new Thread(() -> {
            HeartRateClientInterface nextClient;
            String linea;

            System.out.println("----------------- Transmisión en tiempo real de la frecuencia cardíaca -----------------");

            try {
                while ((linea = fileBuffer.readLine()) != null) {
                    for (int i = 0; i < clientList.size(); i++) {
                        nextClient = (HeartRateClientInterface) clientList.elementAt(i);

                        try {   // invoke the callback method
                            nextClient.notifyMe(Float.parseFloat(linea));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("------------------------- Fin del fichero y de la transmisión --------------------------");
        }).start();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public String sayHello() throws RemoteException {
        return "Servidor de monitorización en tiempo real de un " +
               "paciente en una unidad de cuidados coronarios. Bienvenido!";
    }

    public synchronized void registerForHeartRateTransmission(HeartRateClientInterface callbackClientObject) throws RemoteException{
        // store the callback object into the vector
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("\t[ " + dateFormat.format(new Date()) + " ] Nuevo cliente suscrito!");
        }
    }

    // This remote method allows an object client to 
    // cancel its registration for callback
    // @param id is an ID for the client; to be used by
    // the server to uniquely identify the registered client.
    public synchronized void unregisterForHeartRateTransmission(HeartRateClientInterface callbackClientObject) throws RemoteException {
        if (clientList.removeElement(callbackClientObject))
            System.out.println("\t[ " + dateFormat.format(new Date()) + " ] Cliente ha roto la suscripción");
        else
            System.out.println("unregister: client wasn't registered.");
    }
}
