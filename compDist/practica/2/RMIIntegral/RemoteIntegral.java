import java.rmi.*;

public interface RemoteIntegral extends Remote {
	public double integrate(double start, double stop,
			int numSteps, Evaluatable evalObj)
		throws RemoteException;
}
