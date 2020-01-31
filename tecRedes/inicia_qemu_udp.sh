#!/bin/bash
# Puertos de las VLAN
PORT=1201

kvm -name "debian1" debian1.img -vga cirrus -m 256 -device ne2k_pci,netdev=net0,mac=00:01:01:01:01:01 -netdev user,id=net0,net=192.168.0.0/24,host=192.168.0.1,dhcpstart=192.168.0.2,dns=192.168.0.250,hostfwd=tcp::2222-:22 -device ne2k_pci,netdev=net1,mac=00:02:02:02:02:02 -netdev socket,id=net1,mcast=224.0.0.1:$PORT -device ne2k_pci,netdev=net2,mac=00:03:03:03:03:03 -netdev socket,id=net2,mcast=224.0.0.1:$PORT &

kvm -name "debian2" debian2.img -vga cirrus -m 256 -device ne2k_pci,netdev=net0,mac=00:04:04:04:04:04 -netdev socket,id=net0,mcast=224.0.0.1:$PORT -device ne2k_pci,netdev=net1,mac=00:05:05:05:05:05 -netdev socket,id=net1,mcast=224.0.0.1:$PORT -device ne2k_pci,netdev=net2,mac=00:06:06:06:06:06 -netdev socket,id=net2,mcast=224.0.0.1:$PORT  &
