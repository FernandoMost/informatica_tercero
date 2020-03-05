import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Random;

public class MontecarloImpl extends UnicastRemoteObject implements MontecarloInterface {
    public MontecarloImpl() throws RemoteException {
        super();
    }

    public int doTheMonthecarloMethod(int n) throws RemoteException {
        int laCumplen = 0;
        float x,y;
        Random random = new Random();

        random.setSeed((new Date()).getTime());

        for (int i = 0; i < n; i++) {
            x = random.nextFloat(); y = random.nextFloat();

            if ((x*x + y*y) <= 1)
                laCumplen++;
        }

        return laCumplen;
    }
}