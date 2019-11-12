#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

int main(int argc, char const *argv[]) {

	// 1
	printf("/--------Ejercicio 1--------/\n");
	uint16_t puerto = 18486;	// rango de 0 a 65.535

	printf("%x\n", puerto);		// orden de host

	puerto = htons(puerto);
	printf("%x\n", puerto);		// orden de red

	puerto = ntohs(puerto);
	printf("%x\n", puerto);		// orden de host

	// 2
	printf("\n/--------Ejercicio 2--------/\n");
	char IP[] = "255.89.69.0";
	uint32_t entero;

	entero = inet_network(IP);

	entero = htonl(entero);

	printf("%s\n", inet_ntop(AF_INET, &entero, IP, INET_ADDRSTRLEN));
	printf("%x\n", entero);

	entero = ntohl(entero);		// de host a red

	printf("%s\n", inet_ntop(AF_INET, &entero, IP, INET_ADDRSTRLEN));
	printf("%x\n", entero);
}