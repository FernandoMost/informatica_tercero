import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MontecarloImpl extends UnicastRemoteObject implements MontecarloInterface {
    public MontecarloImpl() throws RemoteException {
        super();
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello, World!" + name;
    }
}