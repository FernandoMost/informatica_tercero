import calculadora.*;
import analizadorTextos.*;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        Calculadora c = new CalculadoraImpl();
        AnalizadorTextos at = new AnalizadorTextosImpl();

        Endpoint.publish("http://localhost:8080/calculadora", c);
        Endpoint.publish("http://localhost:8080/analizadorTextos", at);
    }
}
