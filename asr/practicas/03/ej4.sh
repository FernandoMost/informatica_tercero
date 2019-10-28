#!/bin/bash

check_parametros() {            # parametros: num de parametros, primer parametro (en linea de comandos)
  if [ "$1" -ge 1 ]             # si por lo menos se escribió un parametro
    then
      if [ -n "$(ps aux | awk {'print $1" "$2'} | grep "$2")" ]
        # de la lista de todos los procesos, se extraen las dos primeras columnas: USER y PID
        # con grep se extrae la línea del pid
        then
          if [ "$(ps aux | awk {'print $1" "$2'} | grep "$2" | awk '{print $1;}')" = "$USER" ]
            # se extrae el usuario propietario del pid y se compara con el usuario que ejecuta el script
            then
              return 0          # el pid existe y el usuario es propietario
            else
              echo "No eres root y no tienes permisos para acceder al proceso $2."
              return 1          # el pid existe pero el usuario no es el propietario
          fi
        else
          echo "El proceso $2 no existe."
          return 1
      fi
    else                        # Los parametros introducidos no son los correctos
      echo "Parámetros inválidos"
      echo "Uso: ej4.sh <pid>"  # se imprime la ayuda
      return 1
  fi
}

obtener_valores() {               # argumentos: pid $1, tipo de memoria $2
  result=0                        # variable donde se van a ir sumando los tamaños
  for i in $(cat /proc/"$1"/smaps | grep -w "$2" | awk '{print $2;}'); do
      # en el fichero smaps, se obtienen las líneas con el tipo de tamaño
      # importante la opcion -w en grep, sin ella obtendriamos Size, KernelPageSize y MMUPageSize
      # De estas líneas se extrae solo la segunda columna con awk
      result=$((result+i))        # suma acomulativa
  done
  echo $result
}

if check_parametros $# "$1"     # función que comprueba los parámetros y si el pid es correcto
  then
    total=$(obtener_valores $1 "Size")    # Se obtienen valores, se pasan pid y el tipo de memoria deseada
    echo "Memoria total: $total kB"

    total=$(obtener_valores $1 "Rss")
    echo "Memoria residente: $total kB"

    total=$(obtener_valores $1 "Pss")
    echo "Memoria proporcional: $total kB"

    privada=$(obtener_valores $1 "Private_Clean")
    privada=$((privada+$(obtener_valores $1 "Private_Dirty")))
    echo "Memoria privada: $privada kB"

    compartida=$(obtener_valores $1 "Shared_Clean")
    compartida=$((compartida+$(obtener_valores $1 "Shared_Dirty")))
    echo "Memoria compartida: $compartida kB"

    echo "Privada + compartida: $((privada+compartida)) kB"

    exit 0
  else        # Si los parámetros son incorrectos
    exit 1
fi