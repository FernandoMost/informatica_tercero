import java.io.*;
import java.rmi.*;

public class RemoteIntegralClient{
	public static void main(String[] args) {
		try{
			int RMIPort;         
			String hostName;
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			System.out.println("Enter the RMIRegistry host name:");
			hostName = br.readLine();
			System.out.println("Enter the RMIregistry port number:");
			String portNum = br.readLine();
			RMIPort = Integer.parseInt(portNum);
			String registryURL = 
				"rmi://" + hostName+ ":" + portNum + "/RemoteIntegral";  
// find the remote object and cast it to an interface object
			RemoteIntegral remoteIntegral =
				(RemoteIntegral)Naming.lookup(registryURL);
			
			for(int steps=10; steps<=10000; steps*=10) {
				System.out.println("Approximated with " + steps + "steps:" +
					"\n Integral from 0 to pi of sin(x)=" +
					remoteIntegral.integrate(0.0, Math.PI, steps, new Sin()));
			}
			System.out.println("’Correct’ answer using Math library:" +
				"\n Integral from 0 to pi of sin(x)=" +
				(-Math.cos(Math.PI) - -Math.cos(0.0)));
		} catch(Exception e) {
			System.out.println("Exception in HelloClient: " + e);
		}
	}
}
