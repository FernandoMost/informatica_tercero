#!/usr/bin/env python3

import argparse
import pwd
import random
import string
import subprocess
import crypt

parser = argparse.ArgumentParser(description="Creación de usuarios en python")

parser.add_argument("-u", dest="usuario", required=True, help="Nombre del usuario a crear")
parser.add_argument("-d", dest="departamento", required=True, help="Departamento al que pertenece el usuario")

# true = jefe; false = empleado
grupo = parser.add_mutually_exclusive_group()
grupo.add_argument("-j", dest="esJefe", action='store_true', help="El usuario es de nivel jefe")
grupo.add_argument("-e", dest="esJefe", action='store_false', help="El usuario es de nivel empleado")

parser.add_argument("-p", dest="clave", help="Contraseña de acceso para el usuario, si falta se creará una aleatoria")

args = parser.parse_args()

# ──────────────────────────────────────────────────────────────────

try:
    pwd.getpwnam(args.usuario)
    print("Error. El usuario " + args.usuario + " existe")
except KeyError:
    # detectar que el departamento es válido

    if args.clave is None:
        alfabeto = string.ascii_letters + string.digits
        args.clave = ''.join(random.choice(alfabeto) for i in range(8))

    encriptada = crypt.crypt(args.clave, "sementar_sementarei")

    # directorio /home/{departamento}/{usuario}
    directorio = "/home/" + args.departamento + "/" + args.usuario

    if args.esJefe:
        nivel = "jefes"
    else:
        nivel = "empleados"

    gecos = args.usuario + ", " + args.departamento

    # crear usuario, usuario a grupo, usuario a jefe/empleado
    subprocess.call(['useradd', args.usuario, '-p', encriptada, '-m', '-d', directorio, '-g', args.departamento, '-G', nivel, '-c', gecos, '-s', '/bin/bash'])
    subprocess.call(['passwd', '-x 30', '-w 10', args.usuario])

    # ln -s /home/{departamento}/work /home/{departamento}/{usuario}/comunDepartamento
    subprocess.call(['ln', '-s', '/home/'comun'/comunJefes', '/home/' + args.departamento + '/' + args.usuario + '/comunJefes'])
    # ln -s /home/comun /home/{departamento}/{usuario}/comun
    subprocess.call(['ln', '-s', '/home/comun', '/home/' + args.departamento + '/' + args.usuario + '/comun'])
    # IF ... ln -s /home/comun/comunJefes /home/{departamento}/{usuario}/comunJefes
    if args.esJefe:
        subprocess.call(['ln', '-s', '/home/comun/comunJefes', '/home/' + args.departamento + '/' + args.usuario + '/comunJefes'])

    print("Usuario creado satisfactoriamente")
    print("user:\t" + args.usuario)
    print("dep:\t" + args.departamento)
    print("clave:\t" + args.clave)
    print("home:\t")
    print("gecko:\t")
    print("grupo:\t")
    print("opciones de la contrasena puestas correctamente")
    print("creado:\t")
    print("creado:\t")
