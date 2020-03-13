import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MontecarloInterface extends Remote {
    int doTheMonthecarloMethod(int n) throws RemoteException;
}
