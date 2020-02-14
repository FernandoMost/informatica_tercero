#!/bin/bash
# Puertos de las VLAN
PORT=4000

kvm -name "debian1" debian1.img -vga cirrus -m 256 -device ne2k_pci,netdev=internet,mac=00:01:01:01:01:01 -netdev user,id=internet,hostfwd=tcp::2200-:22,hostfwd=tcp::2300-:2300,hostfwd=tcp::8000-:80 -device ne2k_pci,netdev=lan1,mac=52:54:00:12:34:57 -netdev socket,id=lan1,listen=:$PORT &

sleep 1

kvm -name "debian2" debian2.img -vga cirrus -m 256 -device ne2k_pci,netdev=lan1,mac=00:02:02:02:02:02 -netdev socket,id=lan1,connect=127.0.0.1:$PORT &
