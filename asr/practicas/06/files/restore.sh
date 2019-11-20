#!/bin/bash
cp /etc/shadow.ori /etc/shadow
cp /etc/passwd.ori /etc/passwd 
cp /etc/group.ori /etc/group
cp /etc/gshadow.ori /etc/gshadow
rm -Rf /home/gerencia /home/administracion /home/programacion /home/sistemas /home/comercial /home/rrhh /home/contabilidad /home/comun
