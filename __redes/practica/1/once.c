#include <stdio.h>

#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <ifaddrs.h>

int main() {
	struct ifaddrs *addrs, *item;

	if (getifaddrs(&addrs) < 0)		// comprobbación éxito
		return 1;

	for (item = addrs; item != NULL; item = item->ifa_next) {		// recorriendo la lista enlazada
		switch (item->ifa_addr->sa_family) {		// campo donde está AF_INET o AF_INET6
			case AF_INET: {
				printf("%s\n", item->ifa_name);		// imprime nombre
				struct sockaddr_in *s4 = (struct sockaddr_in*) item->ifa_addr;	//cast a socket IPv4
				char text4[INET_ADDRSTRLEN];

				inet_ntop(AF_INET, &s4->sin_addr, text4, INET_ADDRSTRLEN);
				printf("%s\n", text4);

				s4 = (struct sockaddr_in*) item->ifa_netmask;					// cast a socket IPv4 de la máscara
				inet_ntop(AF_INET, &s4->sin_addr, text4, INET_ADDRSTRLEN);
				printf("%s\n\n", text4);
				break;
			}
			
			case AF_INET6: {
				printf("%s\n", item->ifa_name);
				struct sockaddr_in6 *s6 = (struct sockaddr_in6*) item->ifa_addr;	// cast a IPv6
				char text6[INET6_ADDRSTRLEN];

				inet_ntop(AF_INET6, &s6->sin6_addr, text6, INET6_ADDRSTRLEN);
				printf("%s\n", text6);

				s6 = (struct sockaddr_in6*) item->ifa_netmask;						// cast a socket IPv6 de la máscara
				inet_ntop(AF_INET6, &s6->sin6_addr, text6, INET6_ADDRSTRLEN);
				printf("%s\n\n", text6);
				break;
			}
		}
	}

	freeifaddrs(addrs);		// libera estructuras
	freeifaddrs(item);
	return 0;
}