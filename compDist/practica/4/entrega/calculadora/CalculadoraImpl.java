package calculadora;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.HashMap;

@WebService(
        endpointInterface = "calculadora.Calculadora",
        serviceName = "Calculadora"
)
public class CalculadoraImpl implements Calculadora {
    @Override
    public int suma(int a, int b) {
        return a + b;
    }

    @Override
    public int resta(int a, int b) {
        return a - b;
    }

    @Override
    public int multiplicacion(int a, int b) {
        return a * b;
    }

    @Override
    public double division(int a, int b) {
        return (float) a / b;
    }

    @Override
    public int potencia(int a, int b) {
        return (int) Math.pow(a, b);
    }

    @Override
    public double raizCuadrada(int a) {
        return Math.sqrt(a);
    }

    @Override
    public double logaritmoNeperiano(int a) {
        return Math.log(a);

    }

    @Override
    public int maximo(int[] array) {
        int max = array[0];

        for (int i : array)
            if (i > max)
                max = i;

        return max;
    }

    @Override
    public int minimo(int[] array) {
        int min = array[0];

        for (int i : array)
            if (i < min)
                min = i;

        return min;
    }

    @Override
    public double media(int[] array) {
        int suma = 0;

        for (int i : array)
            suma += i;

        return (double) suma / array.length;
    }

    @Override
    public int mediana(int[] array) {
        Arrays.sort(array);

        if (array.length % 2 == 0)
            return (array[array.length/2] + array[array.length/2 - 1]) / 2;
        else
            return array[array.length/2];
    }

    @Override
    public int moda(int[] array) {
        HashMap<Integer,Integer> hashMapCounter = new HashMap<>();
        int max  = 0, moda = array[0], nueva;

        for (int valor : array) {
            if (hashMapCounter.get(valor) != null) {
                nueva = hashMapCounter.get(valor) + 1;
                hashMapCounter.put(valor, nueva);

                if (nueva > max) {
                    max = nueva;
                    moda = valor;
                }
            } else
                hashMapCounter.put(valor, 1);
        }

        return moda;
    }

    @Override
    public double desviacionTipica(int[] array) {
        double sumatorio = 0.0, media = media(array);

        for(int valor : array)
            sumatorio += Math.pow(valor - media, 2);

        return Math.sqrt(sumatorio/array.length);
    }
}
