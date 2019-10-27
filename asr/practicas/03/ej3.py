#!/usr/bin/env python3

import argparse

parser = argparse.ArgumentParser(description="Etiquetado de regiones")
parser.add_argument("inputFile", help="Matriz de entrada")
args = parser.parse_args()

# ─────────────────────────────────────────────────────────────────────────────────────

file = open(args.inputFile, "r")

matriz = []

for f in file:
    linea = []
    for n in f:
        if n != '\n':
            linea.append(n)
    matriz.append(linea)

for fila in matriz:
    for columna in fila:
        # if columna != 0:
        vecinos = []

        i = matriz.index(fila)
        j = fila.index(columna)

        if i > 0:
            vecinos.append(matriz[i-1][j])
        if j > 0:
            vecinos.append(matriz[i][j-1])

        print(vecinos)
