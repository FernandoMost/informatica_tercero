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
        return nombre;
    }

    @Override
    public String getContrasena() throws RemoteException {
        return contrasena;
    }






    @Override
    public void nuevoMensaje(ChatRoomClientInterfaceForClients origen, String mensaje) throws RemoteException {
        chatController.uncomingMessage(origen, mensaje);
    }

    @Override
    public void nuevaSolicitudAmistad() throws RemoteException {
        chatController.actualizarSolicitudesAmistad();
    }

    @Override
    public void nuevoAmigo() throws RemoteException {
        System.out.println("me han notificao que tengo un nuevo amigo");
        chatController.actualizarSolicitudesAmistad();
    }

    @Override
    public void nuevoAmigoConectado() throws RemoteException {
        chatController.actualizarListaAmigos();
    }

    @Override
    public void amigoDesconectado() throws RemoteException {
        chatController.actualizarListaAmigos();
    }

    @Override
    public void amistadEliminada(String usuario) throws RemoteException {

    }
}
