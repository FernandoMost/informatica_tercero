package myRMIchatP2P.Server;

import myRMIchatP2P.Client.ChatRoomClientInterfaceForServer;

import java.rmi.*;

public interface ChatRoomServerInterface extends Remote {
	public String sayHello() throws RemoteException;

    /** This remote method allows an object client to register for callback
     *  @param cliente is a reference to the object of the client; to be
     *                 used by the server to make its callbacks.
     */
    public void logInChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void registerInChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void solicitudDeAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public boolean existeUsuario(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
    public boolean existeUsuario(String nombre) throws RemoteException;

    public boolean logInCorrecto(String nombre, String contrasena) throws RemoteException;

    public void responderSolicitudDeAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void getAmigos(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void getAmigosConectados(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void getAmigosDiferenciados(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void eliminarAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException;

    public void logOutChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
}
