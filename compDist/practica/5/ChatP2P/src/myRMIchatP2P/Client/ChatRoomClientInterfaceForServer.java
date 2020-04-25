package myRMIchatP2P.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRoomClientInterfaceForServer extends Remote {
	/*
	  La interfaz se centra en la comunicación servidor --> cliente
	 */

	public String getNombre() throws RemoteException;
	public String getContrasena() throws RemoteException;

	/**
	 * Otro usuario envió una solicitud de amistad
	 */
	public void nuevaSolicitudAmistad() throws RemoteException;

	/**
	 * Otro usuario ha aceptado la solicitud de amistad hecha por el cliente, y se añade a la lista de amigos
	 */
	public void nuevoAmigo() throws RemoteException;

	/**
	 * Otro usuario ha accedido a la aplicación, y se añade a la lista de amigos
	 */
	public void nuevoAmigoConectado() throws RemoteException;

	/**
	 * Otro usuario ha salido de la aplicación, y se retira de la lista de amigos
	 */
	public void amigoDesconectado() throws RemoteException;

	/**
	 * Otro usuario ha eliminado al cliente de su grupo de amigos.
	 */
	public void amistadEliminada(String usuario) throws RemoteException;
}
