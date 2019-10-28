#!/usr/bin/env python3

import argparse
import os

parser = argparse.ArgumentParser(description="Desglose de funciones en python")

parser.add_argument("-i", dest="inputFile", required=True, help="Fichero en C de entrada")  # parametros requeridos
parser.add_argument("-o", dest="outputFile", required=True, help="Fichero de salida")

args = parser.parse_args()

# ─────────────────────────────────────────────────────────────────────────────────────


def extract_function(fichero, firstline, set):         # función que extrae toda la función a partir de su primera linea
    corchetes = 0                   # variable que guarda el núm. de corchetes
    strings = []                    # vector con las líneas de la función

    # nombre de la función en la primera linea, de lo anterior al '(', la segunda palabra
    nombre = firstline.split('(')[0].split(' ')[1]

    for c in firstline:             # si se abre un corchete en la primera linea, se suma al contador
        if c == '{':
            corchetes += 1

    strings.append(firstline)       # se añade la primera linea al vector con las lineas

    for line in fichero:            # iterando por el cuerpo de la función
        for c in line:              # se itera por los caracteres de la linea
            if c == '{':            # si se abre un corchete
                corchetes += 1
            elif c == '}':          # si se cierra un corchete
                corchetes -= 1

        strings.append(line)        # se añade al vector con las lineas

        if corchetes == 0:          # todos los corchetes que se abrieron, se cerraron
            break

    set[nombre] = strings           # en el diccionario { nombreFuncion, lineasFuncion[] }

# ─────────────────────────────────────────────────────────────────────────────────────


file = open(args.inputFile, "r")    # abre el fichero de entrada

includes = []       # todas las lineas con includes
floats = {}         # { nombreFuncion:[lineasDeLaFuncion], ...}
voids = {}          # { nombreFuncion:[lineasDeLaFuncion], ...}
resto = []          # resto de lineas

for linea in file:                          # iterando por las lineas del archivo
    if linea.startswith('#include'):        # se trata cada linea según como comiencen
        includes.append(linea)
    elif linea.startswith('float'):
        extract_function(file, linea, floats)
    elif linea.startswith('void'):
        extract_function(file, linea, voids)
    else:
        resto.append(linea)

if floats:  # si se encontraron funciones tipo float
    path = os.path.join('./float')
    if not os.path.exists(path):        # si no existe la carpeta se crea
        os.mkdir(path)

    for f in floats.keys():             # float.keys() contiene todos los nombres de las funciones
        destino = open('./float/' + f + '.c', "w+")     # se crea el archivo .c con el nombre de la funcion
        for i in includes:              # se escriben los includes
            destino.write(i)
        destino.write('\n')
        for l in floats[f]:             # se escriben el cuerpo ded la funcion
            destino.write(l)
        destino.close()

if voids:                               # similar a lo anterior pero con las de tipo void
    path = os.path.join('./void')
    if not os.path.exists(path):
        os.mkdir(path)

    for f in voids.keys():
        destino = open('./void/' + f + '.c', "w+")
        for i in includes:
            destino.write(i)
        destino.write('\n')
        for l in voids[f]:
            destino.write(l)
        destino.close()

nombreSalida = args.outputFile          # nombre del fichero de salida

if not nombreSalida.endswith(".c"):     # si el usuario no le puso .c al final, se lo pone
    nombreSalida += ".c"

destino = open(nombreSalida, "w+")      # se crea el fichero de salida
for i in includes:                      # se escriben los includes
    destino.write(i)

destino.write('\n')

for f in floats.keys():                 # los includes para las funciones de tipo float y void
    destino.write("#include \"float/" + f + ".c\"\n")
for f in voids.keys():
    destino.write("#include \"void/" + f + ".c\"\n")

destino.write('\n')

for l in resto:                         # el resto de funciones
    destino.write(l)

destino.close()
