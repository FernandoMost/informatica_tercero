package myRMIchatP2P.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRoomClientInterfaceForClients extends Remote {
	/*
	  La interfaz se centra en la comunicación cliente --> cliente
	 */

    public String getNombre() throws RemoteException;

    /**
     * Otro usuario envió un mensaje
     */
	public void nuevoMensaje(String mensaje) throws RemoteException;
}
