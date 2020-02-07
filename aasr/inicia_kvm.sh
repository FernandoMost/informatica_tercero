#!/bin/bash
kvm -name "debian1" debian1.img -vga cirrus -m 512 -device ne2k_pci,netdev=net0 -netdev user,id=net0,hostfwd=tcp::2200-:22,hostfwd=tcp::2300-:2300,hostfwd=tcp::8000-:80 &

