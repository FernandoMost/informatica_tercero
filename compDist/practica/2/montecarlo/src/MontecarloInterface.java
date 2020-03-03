import java.rmi.Remote;

public interface MontecarloInterface extends Remote {
    String sayHello(String name) throws java.rmi.RemoteException;
}
