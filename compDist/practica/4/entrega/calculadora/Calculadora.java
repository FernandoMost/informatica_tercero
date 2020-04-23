package calculadora;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface Calculadora {
    @WebMethod(operationName = "suma")
    @WebResult(name = "resultado")
    int suma(@WebParam(name = "sumando1") int a, @WebParam(name = "sumando2") int b);

    @WebMethod(operationName = "resta")
    @WebResult(name = "resultado")
    int resta(@WebParam(name = "restando1") int a, @WebParam(name = "restando2") int b);

    @WebMethod(operationName = "multiplicacion")
    @WebResult(name = "resultado")
    int multiplicacion(@WebParam(name = "parametro1") int a, @WebParam(name = "parametro2") int b);

    @WebMethod(operationName = "division")
    @WebResult(name = "resultado")
    double division(@WebParam(name = "parametro1") int a, @WebParam(name = "parametro2") int b);

    @WebMethod(operationName = "potencia")
    @WebResult(name = "resultado")
    int potencia(@WebParam(name = "base") int a, @WebParam(name = "potencia") int b);

    @WebMethod(operationName = "raizCuadrada")
    @WebResult(name = "resultado")
    double raizCuadrada(@WebParam(name = "parametro") int a);

    @WebMethod(operationName = "logaritmoNeperiano")
    @WebResult(name = "resultado")
    double logaritmoNeperiano(@WebParam(name = "parametro") int a);

    @WebMethod(operationName = "maximo")
    @WebResult(name = "resultado")
    int maximo(@WebParam(name = "array") int[] array);

    @WebMethod(operationName = "minimo")
    @WebResult(name = "resultado")
    int minimo(@WebParam(name = "array") int[] array);

    @WebMethod(operationName = "media")
    @WebResult(name = "resultado")
    double media(@WebParam(name = "array") int[] array);

    @WebMethod(operationName = "mediana")
    @WebResult(name = "resultado")
    int mediana(@WebParam(name = "array") int[] array);

    @WebMethod(operationName = "moda")
    @WebResult(name = "resultado")
    int moda(@WebParam(name = "array") int[] array);

    @WebMethod(operationName = "desviacionTipica")
    @WebResult(name = "resultado")
    double desviacionTipica(@WebParam(name = "array") int[] array);
}
