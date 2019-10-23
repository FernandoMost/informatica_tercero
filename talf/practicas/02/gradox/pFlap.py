# Programado en Python3 por Armando Nogueira Rio

import math
import sys
import os
from warnings import catch_warnings

"""
Clase que representará a nuestro autómata, definido por:
- Un alfabeto
- Un conjunto de estados
- Un conjunto de estados finales
- Una tabla que define las transiciones entre los estados
"""


class automata:
    def __init__(self, estados, estadosfinales, alfabeto, tabla):
        self.estados = estados
        self.estadosfinales = estadosfinales
        self.alfabeto = alfabeto
        self.tabla = tabla
        u = []
        u.append(estados[0])
        self.actual = self.claus(u)
        # El estado actual en nuestro caso es el primer estado que leímos

    """
    Método que nos devuelve el cierre de un estado con la cadena vacía, básicamente
    va haciendo transiciones con la cadena vacía hasta que el conjunto de estados
    no cambie, en ese momento devuelve el conjunto de estados
    """

    def claus(self, estado):
        ret = []

        ter = [];
        ret = list(set(estado));
        while True:
            for i in ret:
                aux = [];
                aux.append(i);
                for j in self.__trans(aux, "λ"):
                    ter.append(j);
            ter = list(set(ter));
            if "" in ter:
                del ter[ter.index("")];
            if set(ret) == set(ter):
                break;
            else:
                ret = ter;
                ter = [];


        ret.sort();
        return ret;

    """
    Método privado que nos devuelve el resultado de ejecutar una transición con
    una entrada de longitud 1 desde un estado determinado, sin incluir la clausura
    de el resultado.
    Dado que simplemente es un método auxiliar de los demás, lo mantendré privado 
    """

    def __trans(self, estado, input):
        ret = [];
        if input not in self.alfabeto:
            # Devuelvo estado vacío
            ret.append("");
            return ret;
        else:
            for i in range(0, len(estado)):
                if estado[i] == "":
                    # El estado vacío va al estado vacío
                    ret.append("");
                else:
                    if input == "λ":
                        ret.append(estado[i]);
                        # Si es la cadena vacía, el resultado es el mismo estado
                    try:
                        a = self.tabla[self.estados.index(estado[i])][self.alfabeto.index(input)]
                        for j in a:
                            ret.append(j);
                            # Estados a los que va con la entrada
                    except ValueError:
                        print(" + Error: ", estado[i], estado, input)
            ret = list(set(ret));
            if "" in ret:
                del ret[ret.index("")];
            if len(ret) == 0:
                ret.append("");
            return ret;

    """
    El método delta nos devolverá los estados que resultan de aplicar la función
    de transición a un conjunto de estados, con una entrada determinada
    """

    def delta(self, estado, input):
        ret = [];
        for i in input:
            if i not in self.alfabeto:
                ret = [];
                ret.append("");
                print(" + Not in alphabet")
                return ret;
            else:
                aux = self.__trans(estado, i);
                for j in aux:
                    ret.append(j);
        # print("Estado: ",estado," - Input: ",input," - Sin clausura: ",ret);
        ret = self.claus(ret);
        return ret;

    """
    Este método nos dice si determinada cadena será aceptada o no por nuestro
    autómata partiendo del estado actual
    """


    """
    Introduce determinada cadena en el autómata, este la procesa y cambia su estado
    de manera acorde
    """

    def run(self, cadena):
        self.actual = self.delta(self.actual, cadena);

    """
    La función reset se encargará volver nuestro autómata al estado inicial
    """

    def reset(self):
        self.actual = selt.alfabeto[0];

    """
    Pone a nuestro autómata en determinado estado
    """

    def setEstado(self, estado):
        for i in estado:
            if i not in self.estados:
                return;
        self.actual = estado;

    """
    Función auxiliar que nos permitirá ver el estado de nuestro autómata por pantalla
    """

    def print(self):
        print(" < Autómata >");
        print(" Estado actual: ", self.actual);
        print(" Alfabeto: ", self.alfabeto);
        print(" Estados finales: ", self.estadosfinales);
        print(" Estados: ", self.estados);
        print();
        print_tabla(self.tabla, self.alfabeto, self.estados);
        print(" </Autómata >");

    """
    Función que nos indica si el autómata se encuentra en un estado de aceptación
    """

    def accepted(self, *estados):
        if len(estados) > 0:
            for i in estados[0]:
                if i in self.estadosfinales:
                    return True;
            return False;
        else:
            for i in self.actual:
                if i in self.estadosfinales:
                    return True;
            return False;


# -------------------------------------------------------------------------------

def print_tabla(tabla, alfabeto, estados):
    aux = [0] * len(tabla[0]);
    suma = [0] * len(tabla[0]);

    for i in tabla:
        for j in i:
            if j[0] != "":
                sup = 0;
                for k in j:
                    sup += len(k);
                if sup > suma[i.index(j)]:
                    suma[i.index(j)] = sup;
                if len(j) > aux[i.index(j)]:
                    aux[i.index(j)] = len(j);

    max = 0;
    for i in estados:
        if len(i) > max:
            max = len(i)

    # print(aux);

    # Número + comillas + coma espacio
    for i in range(0, len(aux)):
        if aux[i] == 0:
            aux[i] = 4;
        else:
            aux[i] = suma[i] + 2 * (aux[i]) + 2 * (aux[i] - 1) + 2;

    # print(aux);

    aux2 = sum(aux) + 3 * len(alfabeto) - 1;

    print(" T", " " * (max - 1), "   | ", sep="", end="");
    for i in range(0, len(alfabeto)):
        if i + 1 == len(alfabeto):
            print("[", " " * math.ceil((aux[i] - 3) / 2), alfabeto[i], " " * math.trunc((aux[i] - 3) / 2), "]", sep="",
                  end="");
        else:
            print("[", " " * math.ceil((aux[i] - 3) / 2), alfabeto[i], " " * math.trunc((aux[i] - 3) / 2), "] | ",
                  sep="", end="");
    print("");

    print(" ---", "-" * (max - 1), " | ", "-" * aux2, sep="");
    for i in range(0, len(tabla)):
        print("", estados[i], " " * (max - len(estados[i])), " | ", end="");
        for j in range(0, len(tabla[i])):

            if not tabla[i][j][0]:
                sep = aux[j] - 4;
            else:
                sepaux = 0;
                for k in tabla[i][j]:
                    sepaux += len(k) + 2;
                sep = aux[j] - ((sepaux + 2) + 2 * (len(tabla[i][j]) - 1));
                if sep < 0:
                    sep = 0;

            if j + 1 == len(tabla[i]):
                print(" " * math.ceil(sep / 2), tabla[i][j], " " * math.floor(sep / 2), sep="", end="");
            else:
                print(" " * math.ceil(sep / 2), tabla[i][j], " " * math.floor(sep / 2), " | ", sep="", end="");
        print("");


def load_file(ruta):
    file = open(ruta, "r");
    # print(file.read());
    i = 0;
    for line in file:
        i += 1;
        line = line.replace("\n", "");
        # print("< Línea ",i," >");
        if i == 1:
            # print("< Leyendo los estados del autómata >");
            # print(line);
            estados = line.split(" ");
            del estados[0];
            j = 0;
            for estado in estados:
                j += 1;
                # print(" - Estado número ", j, ":", estado);
            # print("</Leyendo los estados del autómata >");
        elif i == 2:
            # print("< Leyendo los estados finales del autómata >");
            estadosfinales = line.split(" ");
            del estadosfinales[0];
            j = 0;
            for estado in estadosfinales:
                j += 1;
                # print(" - Estado final número ", j, ":", estado);
            # print("</Leyendo los estados finales del autómata >");
        elif i == 3:
            alfabeto = line.split(" ");
            del alfabeto[0];
            alfabeto.append("λ");
            # print("< El alfabeto del autómata es: {", ', '.join(alfabeto) , "} >");
        elif i == 4:
            # print("< Tabla de transiciones >");
            tabla = [None] * len(estados);
            for j in range(len(estados)):
                tabla[j] = [None] * len(alfabeto);
                # print("Creada tabla de transiciones de tamaño: ",len(tabla),"x",len(tabla[0]));
        elif i > 4:
            t = line.split("#");
            del t[len(t) - 1];
            for j in range(0, len(t)):
                t[j] = t[j].strip();
                # print(t[j].split(" "), "introducido en: [",i-5,",",j,"]");
                tabla[i - 5][j] = t[j].split(" ");
            # print(" - ", estados[i - 5], " | ", " - ".join(t), " | size :", len(t));
        else:
            print(" + Esto nunca debería de aparecer [1]");
            print(line.replace("\n", ""));
        # print("</Línea ",i," >");
    # print("</Tabla de transiciones >");
    return automata(estados, estadosfinales, alfabeto, tabla);


# -------------------------------------------------------------------------------

if "-f" not in sys.argv:
    print(" + Te falta el archivo");
else:
    if len(sys.argv) == 4:
        fil = sys.argv[sys.argv.index("-f") + 1];
        if not os.path.exists(fil):
            print(" + El archivo no existe")
        else:
            af = load_file(fil);
            af.print();
            print();
            u = [];
            if type(af.actual) == list:
                for w in af.actual:
                    u.append(w)
            else:
                u.append(af.actual);
            print("La entrada es:", sys.argv[3]);
            print()
            for i in sys.argv[3]:
                v = [];
                v.append(i)
                k = u;
                u = af.delta(u, i)
                print("Delta(", k, " , ", i, ") = ", u, sep="")
            print()
            if af.accepted(u):
                print("La cadena es aceptada")
            else:
                print("La cadena no es aceptada")
    else:
        print(" + Te falta la cadena");
