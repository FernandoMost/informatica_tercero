#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

void printServent(struct servent *Servicio) {
	int i;

	if (Servicio == NULL)
		exit(1);

	printf("Nombre oficial del servicio: %s\n", Servicio->s_name);

	if (Servicio->s_aliases[0] != NULL) {
		printf("Lista de alias del servicio:\n");
		for (i = 0; Servicio->s_aliases[i] != NULL; i++)
			printf("\tAlias[%d]: %s\n", i, Servicio->s_aliases[i]);
	}

	printf("Número de puerto: %d\n", ntohs(Servicio->s_port));
	printf("Nombre del protocolo que usa este servicio: %s\n\n\n", Servicio->s_proto);
}

void main() {
	struct servent *S;

	// 1
	
	printf("%d %d\n\n", IPPORT_RESERVED, IPPORT_USERRESERVED);	// Macros, valor mínimo y máximo

	// 2

	S = getservbyname("http","tcp"); printServent(S);
	S = getservbyname("telnet","tcp"); printServent(S);
	S = getservbyname("smtp","tcp"); printServent(S);

	// 3

	S = getservbyport(htons(7),"tcp"); printServent(S);
	S = getservbyport(htons(21),"tcp"); printServent(S);
	S = getservbyport(htons(110),"tcp"); printServent(S);

	// 4

	setservent(1);

	while((S = getservent()) != NULL)
		printServent(S);

	endservent();
}