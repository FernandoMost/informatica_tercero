#!/bin/bash

# VOLVER AL BACKUP
rm -rf /home/gerencia
rm -rf /home/administracion
rm -rf /home/programacion
rm -rf /home/sistemas
rm -rf /home/comercial
rm -rf /home/rrhh
rm -rf /home/contabilidad

departamentos=(gerencia administracion programacion sistemas comercial rrhh contabilidad)

for d in "${departamentos[@]}"; do
  # echo "$d"
  # CREAR GRUPOS PARA CADA DEPARTAMENTO
  groupadd "$d"

  # CREAR DIRECTORIOS PARA CADA DEPARTAMENTO
  # dentro de cada uno, crear ./work
  mkdir -pv /home/"$d"/work

  # directorios de nuevos usuarios se crearán en estos directorios
  # solo pueden entrar ese directorio (ver .txt)
  chmod o-rwx /home/"$d"/
  chmod -v 770 /home/"$d"/work
  chown -v :"$d" /home/"$d"/
done

# CREAR GRUPOS PARA JEFES Y EMPLEADOS
groupadd jefes
groupadd empleados


#sgid
#chmod acá abajo

# CREAR DIRECTORIO COMUN
mkdir -pv /home/comun/comunJefes
chmod -v +t /home/comun 

# dentro /comunJefes, exclusivo para jefes
chmod o-rwx /home/comun/comunJefes
chown -v :jefes /home/comun/comunJefes

# (tods pueden acceder y crear, sólo root puede borrar)
# sus ficheros solo pueden ser borrados por el creador y root
chmod -v +t /home/comun/comunJefes