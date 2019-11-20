#!/bin/bash

# conjunto con los departamentos
departamentos=(gerencia administracion programacion sistemas comercial rrhh contabilidad)

for d in "${departamentos[@]}"; do    # iterando por los departamentos
  # creando el grupo del departamento
  groupadd "$d"

  # directorio para el departamento, dentro de /home
  # dentro del mismo, directorio work
  # con la opción -p de mkdir creamos ambos en un sólo comando
  mkdir -pv /home/"$d"/work

  # directorios de nuevos usuarios se crearán en estos directorios
  # solo pueden entrar ese directorio (ver .txt)

  # quitamos todos los permisos de Others en /home/departamento
  chmod -v o-rwx /home/"$d"/

  # todos los permisos de propietario y grupo en /home/work
  chmod -v 770 /home/"$d"/work

  # se cambia de propietario todos los contenidos de manera recursiva
  # de /home/departamento a todos los usuarios del grupo correspondiente
  chgrp -Rv "$d" /home/"$d"/

  # barra estética
  echo "───────────────────────────────────────────"
done

# los dos grupos adicionales
groupadd jefes
groupadd empleados

# directorio común y comúnJefes dentro de este
# con la opción -p de mkdir creamos ambos en un sólo comando
mkdir -pv /home/comun/comunJefes

# todos los permisos en el directorio común
chmod -v 777 /home/comun/

# sin los permisos para otros en el directorio comun para jefes
chmod -v 770 /home/comun/comunJefes

# cambiando el propietario de /comunJefes al grupo de jefes
chgrp -v jefes /home/comun/comunJefes

# cambiando el sticky bit en /comun y /comunJefes
# así solo pueden ser eliminados por su creador o root
chmod -Rv +t /home/comun/