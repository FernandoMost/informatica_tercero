#!/bin/bash
# Puertos de las VLAN
PORT=1201

kvm -name "debian1" debian1.img -vga cirrus -m 256 -net nic,vlan=0,macaddr=00:01:01:01:01:01 -net user,net=192.168.0.0/24,host=192.168.0.1,dhcpstart=192.168.0.2,dns=192.168.0.250,hostfwd=tcp::2222-:22 -net nic,vlan=1,macaddr=00:02:02:02:02:02 -net nic,vlan=1,macaddr=00:03:03:03:03:03 -net socket,vlan=1,listen=:$PORT &

sleep 1

kvm -name "debian2" debian2.img -vga cirrus -m 256 -net nic,vlan=1,macaddr=00:04:04:04:04:04 -net nic,vlan=1,macaddr=00:05:05:05:05:05 -net nic,vlan=1,macaddr=00:06:06:06:06:06 -net socket,vlan=1,connect=127.0.0.1:$PORT &
