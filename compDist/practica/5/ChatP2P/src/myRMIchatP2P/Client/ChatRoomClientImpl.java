package myRMIchatP2P.Client;

import controllers.ChatController;

import java.rmi.*;
import java.rmi.server.*;

public class ChatRoomClientImpl extends UnicastRemoteObject implements ChatRoomClientInterfaceForServer, ChatRoomClientInterfaceForClients {
    private String nombre;
    private String contrasena;
    private ChatController chatController;

    public ChatRoomClientImpl(String nombre, String contrasena, ChatController chatController) throws RemoteException {
        super();
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.chatController = chatController;
    }

    @Override
    public String getNombre() throws RemoteException {
        return null;
    }

    @Override
    public String getContrasena() throws RemoteException {
        return null;
    }






    @Override
    public void nuevoMensaje(String mensaje) throws RemoteException {

    }

    @Override
    public void nuevaSolicitudAmistad(String usuario) throws RemoteException {

    }

    @Override
    public void nuevoAmigo(String usuario) throws RemoteException {

    }

    @Override
    public void nuevoAmigoConectado(String usuario) throws RemoteException {

    }

    @Override
    public void amigoDesconectado(String usuario) throws RemoteException {

    }

    @Override
    public void amistadEliminada(String usuario) throws RemoteException {

    }
}
