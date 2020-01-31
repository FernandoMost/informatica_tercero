#!/bin/bash

sudo kvm -name "debian1" -hda debian1.img -vga cirrus -m 256 -device ne2k_pci,netdev=net0,mac=00:01:01:01:01:01 -netdev tap,id=net0,script=qemu-ifup -device ne2k_pci,netdev=net1,mac=00:02:02:02:02:02 -netdev tap,id=net1,script=qemu-ifup -device ne2k_pci,netdev=net2,mac=00:03:03:03:03:03 -netdev tap,id=net2,script=qemu-ifup &

sudo kvm -name "debian2" -hda debian2.img -vga cirrus -m 256 -device ne2k_pci,netdev=net0,mac=00:04:04:04:04:04 -netdev tap,id=net0,script=qemu-ifup -device ne2k_pci,netdev=net1,mac=00:05:05:05:05:05 -netdev tap,id=net1,script=qemu-ifup -device ne2k_pci,netdev=net2,mac=00:06:06:06:06:06 -netdev tap,id=net2,script=qemu-ifup &

