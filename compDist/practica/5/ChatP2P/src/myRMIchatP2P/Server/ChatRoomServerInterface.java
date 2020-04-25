package myRMIchatP2P.Server;

import myRMIchatP2P.Client.ChatRoomClientInterfaceForClients;
import myRMIchatP2P.Client.ChatRoomClientInterfaceForServer;

import java.rmi.*;
import java.util.HashSet;
import java.util.Vector;

public interface ChatRoomServerInterface extends Remote {
    public String sayHello() throws RemoteException;
    public void logInChatRoom(ChatRoomClientInterfaceForServer cliente, ChatRoomClientInterfaceForClients clienteParaCliente) throws RemoteException;
    public void guardarNuevoUsuario(String usuario, String contrasena) throws RemoteException;
    public void guardarsolicitudDeAmistad(ChatRoomClientInterfaceForServer origen, String destino) throws RemoteException;
    public boolean existeUsuario(String nombre) throws RemoteException;
    public boolean existeAmistad(ChatRoomClientInterfaceForServer amigo1, String amigo2) throws RemoteException;
    public boolean existeYaUnaSolicitud(ChatRoomClientInterfaceForServer amigo1, String amigo2) throws RemoteException;
    public boolean tieneSolicitudes(ChatRoomClientInterfaceForServer usuario) throws RemoteException;
    public HashSet<String> getSolicitudes(ChatRoomClientInterfaceForServer usuario) throws RemoteException;
    public boolean logInCorrecto(String nombre, String contrasena) throws RemoteException;
    public void responderSolicitudDeAmistad(ChatRoomClientInterfaceForServer origen, String destino, boolean acepta) throws RemoteException;
    public HashSet<String> getAmigos(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
    public Vector<ChatRoomClientInterfaceForClients> getAmigosConectados(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
    public void eliminarAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
    public void logOutChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException;
}
