#!/usr/bin/env python3

import argparse
import pwd
import grp
import random
import string
import subprocess
import crypt

# ───────────────────────────────────

# Determinando los argumentos del programa
parser = argparse.ArgumentParser(description="Creación de usuarios en python")

# Argumentos obligatorios
parser.add_argument("-u", dest="usuario", required=True, help="Nombre del usuario a crear")
parser.add_argument("-d", dest="departamento", required=True, help="Departamento al que pertenece el usuario")

# Grupo para determinar el nivel del usuario      true = jefe; false = empleado
grupo = parser.add_mutually_exclusive_group()
grupo.add_argument("-j", dest="esJefe", action='store_true', help="El usuario es de nivel jefe")
grupo.add_argument("-e", dest="esJefe", action='store_false', help="El usuario es de nivel empleado")

# Argumento para la contraseña de acceso
parser.add_argument("-p", dest="clave", help="Contraseña de acceso, si falta se creará una aleatoria")

args = parser.parse_args()

# ─────────────────────────────────────────────────────────────────────────────────────

try:
    pwd.getpwnam(args.usuario)  # recuperando el usuario

    # si existe no salta ninguna excepción, seguimos en este bloque
    print("Error. El usuario " + args.usuario + " existe")
except KeyError:
    # si el usuario no existe, salta la excepcion KeyError y pasamos a este bloque de código
    # de manera similar, se identifica que el grupo {departamento} exista

    try:
        # si el departamento no existe como grupo, salta la excepción KeyError
        grp.getgrnam(args.departamento)

        # cadena con el directorio del usuario
        directorio = "/home/" + args.departamento + "/" + args.usuario

        # descripción del usuario
        gecos = args.usuario + ", " + args.departamento

        # determinando el grupo secundario según los argumentos -j/-e
        if args.esJefe:
            nivel = "jefes"
        else:
            nivel = "empleados"

        # si no se proporciona una contraseña, se genera una aleatoria
        if args.clave is None:
            alfabeto = string.digits + string.ascii_letters + string.punctuation    # caracteres posibles
            args.clave = ''.join(random.choice(alfabeto) for i in range(8))         # cadena aleatoria de tamaño 8

        encriptada = crypt.crypt(args.clave, "sementar_sementarei")                 # encriptación usando el módulo crypt

        # crear usuario, usuario a grupo, usuario a jefe/empleado
        subprocess.call(['useradd', args.usuario,       # nombre del usuario
                         '-p', encriptada,              # contraseña encriptada
                         '-m', '-d', directorio,        # creación del directorio en /home
                         '-g', args.departamento,       # grupo principal, departamento
                         '-G', nivel,                   # grupo secundario, jefe/empleado
                         '-c', gecos,                   # descripción
                         '-s', '/bin/bash'])            # shell
        print("Usuario creado satisfactoriamente")
        print("user:\t" + args.usuario)
        print("dep:\t" + args.departamento)
        print("clave:\t" + args.clave)
        print("home:\t" + directorio)
        print("gecko:\t" + gecos)
        print("grupo:\t" + nivel)

        subprocess.call(['passwd', '-x 30', '-w 10', args.usuario])     # validez de la contraseña

        # enlace simbólico al work de su departamento
        subprocess.call(['ln', '-s', '/home/' + args.departamento + '/work', directorio + '/comunDepartamento'])
        print("creado:\t/home/" + args.departamento + "/work")

        # enlace simbólico al directorio comun de todos
        subprocess.call(['ln', '-s', '/home/comun', directorio + '/comun'])
        print("creado:\t/home/comun/")

        # si es jefe, enlace simbólico al directorio común entre los jefes
        if args.esJefe:
            subprocess.call(['ln', '-s', '/home/comun/comunJefes', directorio + '/comunJefes'])
            print("creado:\t/home/comun/comunJefes")
    except KeyError:
        # en caso de que el grupo no exista, el script se detiene a través de esta excepción
        print("Error. El departamento " + args.departamento + " no existe")
