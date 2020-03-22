import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RemoteIntegralImpl extends UnicastRemoteObject
		implements RemoteIntegral {

	public RemoteIntegralImpl() throws RemoteException {}

	public double sum(double start, double stop, double stepSize,
			Evaluatable evalObj) {
		double sum = 0.0, current = start;
		while(current <= stop) {
			sum += evalObj.evaluate(current);
			current += stepSize;
		}
		return(sum);
	}

	public double integrate(double start, double stop, int numSteps,
			Evaluatable evalObj) {
		double stepSize = (stop - start) / (double)numSteps;
		start = start + stepSize / 2.0;
		return(stepSize * sum(start, stop, stepSize, evalObj));
	}
}
