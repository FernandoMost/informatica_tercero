package myRMIchatP2P.Server;

import myRMIchatP2P.Client.ChatRoomClientInterfaceForClients;
import myRMIchatP2P.Client.ChatRoomClientInterfaceForServer;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashSet;
import java.util.Vector;

public class ChatRoomServerImpl extends UnicastRemoteObject implements ChatRoomServerInterface {
    private Vector<ChatRoomClientInterfaceForServer> listaClientes;
    private Vector<ChatRoomClientInterfaceForClients> listaClientesParaClientes;
    private final DAOChatRoom dataAccessObject;

    public ChatRoomServerImpl() throws RemoteException {
        super();
        listaClientes = new Vector<>();
        listaClientesParaClientes = new Vector<>();
        dataAccessObject = new DAOChatRoom();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String sayHello() {
        return "Servidor destinado a una sala de Chat, usando Java RMi y tecnología P2P. Bienvenido!";
    }

    @Override
    public void logInChatRoom(ChatRoomClientInterfaceForServer cliente, ChatRoomClientInterfaceForClients clienteParaCliente) throws RemoteException {
        // store the callback object into the vector
        if (!listaClientes.contains(cliente)) {
            listaClientes.addElement(cliente);
            listaClientesParaClientes.addElement(clienteParaCliente);
            System.out.println("\tNuevo cliente registrado.");
        }

        // iterando por los clientes conectados, si son amigos actualizan sus listas
        HashSet<String> amigos = getAmigos(cliente);
        for (ChatRoomClientInterfaceForServer clienteConectado : listaClientes) {
            if (amigos.contains(clienteConectado.getNombre()))
                clienteConectado.nuevoAmigoConectado();
        }
    }

    @Override
    public synchronized void guardarNuevoUsuario(String usuario, String contrasena) throws RemoteException {
        dataAccessObject.guardarNuevoUsuario(usuario, contrasena);
    }

    @Override
    public void guardarsolicitudDeAmistad(ChatRoomClientInterfaceForServer origen, String destino) throws RemoteException {
        dataAccessObject.guardarNuevaSolicitudAmistad(origen.getNombre(), destino);

        for (ChatRoomClientInterfaceForServer conectado : listaClientes)
            if (conectado.getNombre().equals(destino)) {
                conectado.nuevaSolicitudAmistad();
                break;
            }
    }

    @Override
    public boolean existeUsuario(String nombre) throws RemoteException {
        return dataAccessObject.usuarioExiste(nombre);
    }

    @Override
    public boolean existeAmistad(ChatRoomClientInterfaceForServer amigo1, String amigo2) throws RemoteException {
        return dataAccessObject.existeAmistad(amigo1.getNombre(), amigo2);
    }

    @Override
    public boolean existeYaUnaSolicitud(ChatRoomClientInterfaceForServer amigo1, String amigo2) throws RemoteException {
        return dataAccessObject.existeYaUnaSolicitud(amigo1.getNombre(), amigo2);
    }

    @Override
    public boolean logInCorrecto(String nombre, String contrasena) throws RemoteException {
        return dataAccessObject.loginCorrecto(nombre, contrasena);
    }

    @Override
    public void responderSolicitudDeAmistad(ChatRoomClientInterfaceForServer yo, String elOtro, boolean acepta) throws RemoteException {
        if (acepta) {
            dataAccessObject.aceptarSolicitudAmistad(yo.getNombre(), elOtro);

            for (ChatRoomClientInterfaceForServer conectado : listaClientes)
                if (conectado.getNombre().equals(elOtro)) {
                    conectado.nuevoAmigo();
                    System.out.println("avisando a " + elOtro + " que su solicitud a " + yo.getNombre() + " fue aceptada");
                    break;
                }
        } else
            dataAccessObject.rechazarSolicitudAmistad(yo.getNombre(), elOtro);
    }

    @Override
    public boolean tieneSolicitudes(ChatRoomClientInterfaceForServer usuario) throws RemoteException {
        return dataAccessObject.tengoSolicitudesAmistad(usuario.getNombre());
    }

    @Override
    public HashSet<String> getSolicitudes(ChatRoomClientInterfaceForServer usuario) throws RemoteException {
        return dataAccessObject.getSolicitudesAmistad(usuario.getNombre());
    }

    @Override
    public HashSet<String> getAmigos(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        return dataAccessObject.getAmigos(cliente.getNombre());
    }

    @Override
    public Vector<ChatRoomClientInterfaceForClients> getAmigosConectados(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        HashSet<String> nombresAmigos = dataAccessObject.getAmigos(cliente.getNombre());
        Vector<ChatRoomClientInterfaceForClients> objetosAmigos = new Vector<>();

        for (ChatRoomClientInterfaceForClients c : listaClientesParaClientes)
            if (nombresAmigos.contains(c.getNombre()))
                objetosAmigos.add(c);

        return objetosAmigos;
    }

    @Override
    public void eliminarAmistad(ChatRoomClientInterfaceForServer cliente) throws RemoteException {

    }

    @Override
    public void logOutChatRoom(ChatRoomClientInterfaceForServer cliente) throws RemoteException {
        if (listaClientes.contains(cliente)) {
            listaClientes.removeElement(cliente);
            listaClientesParaClientes.removeElement(cliente);

            System.out.println("\tCliente ha roto la suscripción");
        } else
            System.out.println("\tCliente no estaba registrado");

        // iterando por los clientes conectados, si son amigos actualizan sus listas
        HashSet<String> amigos = getAmigos(cliente);
        for (ChatRoomClientInterfaceForServer clienteConectado : listaClientes) {
            if (amigos.contains(clienteConectado.getNombre()))
                clienteConectado.amigoDesconectado();
        }
    }
}
