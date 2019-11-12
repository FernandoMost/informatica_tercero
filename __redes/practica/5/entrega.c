#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netdb.h>
#include <string.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>

void main(int argc, char const *argv[]) {
	struct addrinfo hints, *result, *aux;
	char textIPv4[INET_ADDRSTRLEN], textIPv6[INET6_ADDRSTRLEN];

	memset(&hints, 0, sizeof(struct addrinfo));	// Los miembros en el parámetro hints que no se usen deben contener o bien 0 o un puntero NULL.
	hints.ai_family = AF_UNSPEC;				// Desired address family for the returned addresses, AF_UNSPEC indicates any  address family (either IPv4 or IPv6)
	hints.ai_socktype = SOCK_STREAM;			// preferred socket type
	hints.ai_flags = AI_CANONNAME;				// protocol for the returned socket addresses

	if (getaddrinfo(argv[1], NULL, &hints, &result) == -1) {
		printf("Error\n");
		exit(-1);
	}

	for (aux = result; aux != NULL; aux = aux->ai_next) {
		switch (aux->ai_family) {
			case AF_INET: {
				struct sockaddr_in *socket4 = (struct sockaddr_in*)aux->ai_addr;

				inet_ntop(AF_INET, &(socket4->sin_addr), textIPv4, INET_ADDRSTRLEN);

				printf("Dirección IPv4: %s\n", textIPv4);
				printf("Nombre canónico: %s\n", aux->ai_canonname);

				break;
			}
			case AF_INET6: {
				struct sockaddr_in6 *socket6 = (struct sockaddr_in6*)aux->ai_addr;

				inet_ntop(AF_INET6, &(socket6->sin6_addr), textIPv6, INET6_ADDRSTRLEN);

				printf("Dirección IPv6: %s\n", textIPv6);
				printf("Nombre canónico: %s\n", aux->ai_canonname);

				break;
			}
		}
		printf("\n");
	}

	freeaddrinfo(result);
	freeaddrinfo(aux);
}