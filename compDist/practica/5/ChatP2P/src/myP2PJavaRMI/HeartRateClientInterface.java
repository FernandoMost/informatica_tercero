package MyPublishSubscribe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HeartRateClientInterface extends Remote {
	public String notifyMe(float RR) throws RemoteException;
}
