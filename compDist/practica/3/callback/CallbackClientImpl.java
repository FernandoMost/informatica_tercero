import java.rmi.*;
import java.rmi.server.*;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {
	public CallbackClientImpl() throws RemoteException {
		super( );
	}

	public String notifyMe(String message) {
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }
}
