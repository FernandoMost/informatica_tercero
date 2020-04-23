package analizadorTextos;

import javax.jws.WebService;
import java.util.HashMap;

@WebService(
        endpointInterface = "analizadorTextos.AnalizadorTextos",
        serviceName = "AnalizadorTextos"
)

public class AnalizadorTextosImpl implements AnalizadorTextos {
    @Override
    public int cuentaPalabrasTotales(String texto) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }

        String[] palabras = texto.split("\\s+");

        return palabras.length;
    }

    @Override
    public int cuentaCaracteresTotales(String texto) {
        return texto.length();
    }

    @Override
    public int cuentaFrasesTotales(String texto) {
        int total = 1;

        if (texto == null || texto.isEmpty())
            return 0;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (c == '.' || c == '?' || c == '!')
                total++;
        }

        return total-1;
    }

    @Override
    public int cuentaPalabra(String texto, String palabra) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }

        int total = 0;
        String[] palabras = texto.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");

        for (String i : palabras)
            if (i.equals(palabra))
                total++;


        return total;
    }

    @Override
    public String palabraMasUsada(String texto) {
        HashMap<String,Integer> hashMapCounter = new HashMap<>();
        int max = 0; String moda = null;

        String[] palabras = texto.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");

        for (String palabra : palabras) {
            if (hashMapCounter.get(palabra) != null) {
                int nuevaCantidad = hashMapCounter.get(palabra) + 1;
                hashMapCounter.put(palabra, nuevaCantidad);

                if (nuevaCantidad > max) {
                    max = nuevaCantidad;
                    moda = palabra;
                }
            } else
                hashMapCounter.put(palabra, 1);
        }

        return moda;
    }

    @Override
    public String palabraMenosUsada(String texto) {
        HashMap<String,Integer> hashMapCounter = new HashMap<>();
        String menosUsada = null;

        String[] palabras = texto.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        for (String palabra : palabras) {
            if (hashMapCounter.get(palabra) != null) {
                hashMapCounter.put(palabra, hashMapCounter.get(palabra) + 1);
            } else {
                hashMapCounter.put(palabra, 1);
                menosUsada = palabra;
            }
        }

        return menosUsada;
    }

    @Override
    public String reemplazarPalabra(String texto, String palabraVieja, String palabraNueva) {
        //return texto.replaceAll("(?i)\b" + palabraVieja + "\b", palabraNueva);
        return texto.replaceAll(palabraVieja, palabraNueva);
    }
}
