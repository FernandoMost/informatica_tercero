package MyPublishSubscribe;

import MyPublishSubscribe.HeartRateClientGraphics.Controllers.TransmissionController;

import java.rmi.*;
import java.rmi.server.*;

public class HeartRateClientImpl extends UnicastRemoteObject implements HeartRateClientInterface {
	private TransmissionController transmissionController;

    public HeartRateClientImpl(TransmissionController transmissionController) throws RemoteException {
		super( );
		this.transmissionController = transmissionController;
	}

	public String notifyMe(float RR) {
      String returnMessage = "RR received: " + RR;
      System.out.println(returnMessage);
      transmissionController.addDataToChart(RR);
      return returnMessage;
   }
}
