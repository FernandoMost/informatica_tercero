package analizadorTextos;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface AnalizadorTextos {
    @WebMethod(operationName = "cuentaPalabraTotales")
    @WebResult(name = "resultado")
    int cuentaPalabrasTotales(@WebParam(name = "texto") String texto);

    @WebMethod(operationName = "cuentaCaracteresTotales")
    @WebResult(name = "resultado")
    int cuentaCaracteresTotales(@WebParam(name = "texto") String texto);

    @WebMethod(operationName = "cuentaFrasesTotales")
    @WebResult(name = "resultado")
    int cuentaFrasesTotales(@WebParam(name = "texto") String texto);

    @WebMethod(operationName = "cuentaPalabra")
    @WebResult(name = "resultado")
    int cuentaPalabra(@WebParam(name = "texto") String texto, @WebParam(name = "palabra") String palabra);

    @WebMethod(operationName = "palabraMasUsada")
    @WebResult(name = "resultado")
    String palabraMasUsada(@WebParam(name = "texto") String texto);

    @WebMethod(operationName = "palabraMenosUsada")
    @WebResult(name = "resultado")
    String palabraMenosUsada(@WebParam(name = "texto") String texto);

    @WebMethod(operationName = "reemplazarPalabra")
    @WebResult(name = "resultado")
    String reemplazarPalabra(@WebParam(name = "texto") String texto, @WebParam(name = "palabraVieja") String palabraVieja, @WebParam(name = "palabraNueva") String palabraNueva);
}
