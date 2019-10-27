#!/bin/bash

check_parametros() {
  if [ "$1" -ge 1 ]
    then
      if [ -n "$(ps aux | awk {'print $1" "$2'} | grep "$2")" ]
        then
          if [ "$(ps aux | awk {'print $1" "$2'} | grep "$2" | awk '{print $1;}')" = "$USER" ]
            then
              return 0
            else
              echo "No eres root y no tienes permisos para acceder al proceso $2."
              return 1
          fi
        else
          echo "El proceso $2 no existe."
          return 1
      fi
    else
      echo "Parámetros inválidos"
      echo "Uso: ej4.sh <pid>"
      return 1
  fi
}

obtener_valores() {
  result=0
  for i in $(cat /proc/"$1"/smaps | grep -w "$2" | awk '{print $2;}'); do
      result=$((result+i))
  done
  echo $result
}

if check_parametros $# "$1"
  then
    total=$(obtener_valores $1 "Size")
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
  else
    exit 1
fi