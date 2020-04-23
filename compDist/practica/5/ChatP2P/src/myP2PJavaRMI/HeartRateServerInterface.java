package MyPublishSubscribe;

import java.rmi.*;

public interface HeartRateServerInterface extends Remote {
	public String sayHello() throws java.rmi.RemoteException;



    // This remote method allows an object client to 
    // register for callback
    // @param callbackClientObject is a reference to the
    //        object of the client; to be used by the server
    //        to make its callbacks.

    public void registerForHeartRateTransmission(HeartRateClientInterface callbackClientObject) throws RemoteException;



    // This remote method allows an object client to 
    // cancel its registration for callback

    public void unregisterForHeartRateTransmission(HeartRateClientInterface callbackClientObject) throws RemoteException;
}
