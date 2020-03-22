import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Random;

public class MontecarloImpl extends UnicastRemoteObject implements MontecarloInterface {
    MontecarloImpl() throws RemoteException {
        super();
    }

    public int doTheMonthecarloMethod(int n) {
        int laCumplen = 0;
        float x,y;
        Random random = new Random();

        random.setSeed((new Date()).getTime());     // Semilla para generar números aleatorios

        for (int i = 0; i < n; i++) {
            x = random.nextFloat(); y = random.nextFloat();     // X e Y aleatorias

            if ((x*x + y*y) <= 1)                   // condición de montecarlo
                laCumplen++;
        }

        return laCumplen;
    }
}
