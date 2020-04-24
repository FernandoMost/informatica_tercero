package myRMIchatP2P.Server;

import myRMIchatP2P.Client.ChatRoomClientInterfaceForServer;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;

public class ChatRoomServerImpl extends UnicastRemoteObject implements ChatRoomServerInterface {
    private Vector<ChatRoomClientInterfaceForServer> listaClientes;
    private final DAOChatRoom dataAccessObject;

    public ChatRoomServerImpl() throws RemoteException {
        super();
        listaClientes = new Vector<>();
        dataAccessObject = new DAOChatRoom();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String sayHello() {
        return "Servidor destinado a una sala de Chat, usando Java RMi y tecnología P2P. Bienvenido!";
    }

    @Override
    public void logInChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        // store the callback object into the vector
        if (!(listaClientes.contains(cliente))) {
            listaClientes.addElement(cliente);
            System.out.println("Nuevo cliente registrado.");
        }
    }

    public synchronized void registerInChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        dataAccessObject.guardarNuevoUsuario(cliente.getNombre(), cliente.getContrasena());
    }

    @Override
    public void solicitudDeAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public boolean existeUsuario(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        return this.existeUsuario(cliente.getNombre());
    }

    @Override
    public boolean existeUsuario(String nombre) throws RemoteException {
        return dataAccessObject.usuarioExiste(nombre);
    }

    @Override
    public boolean logInCorrecto(String nombre, String contrasena) throws RemoteException {
        return dataAccessObject.loginCorrecto(nombre, contrasena);
    }

    @Override
    public void responderSolicitudDeAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void getAmigos(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void getAmigosConectados(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void getAmigosDiferenciados(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void eliminarAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void logOutChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        if (listaClientes.removeElement(cliente))
            System.out.println("Cliente ha roto la suscripción");
        else
            System.out.println("unregister: client wasn't registered.");
    }
}
