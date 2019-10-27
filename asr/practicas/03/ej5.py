#!/usr/bin/env python3

import argparse
import os

parser = argparse.ArgumentParser(description="Desglose de funciones")

parser.add_argument("-i", dest="inputFile", required=True, help="Fichero en C de entrada")
parser.add_argument("-o", dest="outputFile", required=True, help="Fichero de salida")

args = parser.parse_args()


# ─────────────────────────────────────────────────────────────────────────────────────

def extract_function(fichero, firstline, set):
    corchetes = 0
    strings = []

    nombre = firstline.split('(')[0].split(' ')[1]

    for c in firstline:
        if c == '{':
            corchetes += 1
        elif c == '}':
            corchetes -= 1

    strings.append(firstline)

    for line in fichero:
        for c in line:
            if c == '{':
                corchetes += 1
            elif c == '}':
                corchetes -= 1

        strings.append(line)

        if corchetes == 0:
            break

    set[nombre] = strings


# ─────────────────────────────────────────────────────────────────────────────────────

file = open(args.inputFile, "r")

includes = []
floats = {}
voids = {}
resto = []

for linea in file:
    firstWord = linea.split(' ')[0]

    if linea.startswith('#include'):
        includes.append(linea)
    elif linea.startswith('float'):
        extract_function(file, linea, floats)
    elif linea.startswith('void'):
        extract_function(file, linea, voids)
    else:
        resto.append(linea)

if floats:
    path = os.path.join('./float')
    if not os.path.exists(path):
        os.mkdir(path)

    for f in floats.keys():
        destino = open('./float/' + f + '.c', "w+")
        for i in includes:
            destino.write(i)
        destino.write('\n')
        for l in floats[f]:
            destino.write(l)
        destino.close()

if voids:
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

destino = open(args.outputFile, "w+")
for i in includes:
    destino.write(i)

destino.write('\n')

for f in floats.keys():
    destino.write("#include \"float/" + f + ".c\"\n")
for f in voids.keys():
    destino.write("#include \"void/" + f + ".c\"\n")

destino.write('\n')

for l in resto:
    destino.write(l)

destino.close()
