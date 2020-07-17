#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

void printHost(struct hostent *H) {
	struct in_addr IP;
	int i;

	printf("Nombre del host: %s\n", H->h_name);

	if (H->h_aliases[0] != NULL) {
		printf("Lista de alias del host:\n");
		for (i = 0; H->h_aliases[i] != NULL; i++)
			printf("\tAlias[%d]: %s\n", i, H->h_aliases[i]);
	}

	printf("Tipo de dirección: ");
	if (H->h_addrtype == AF_INET)
		printf("IPv4\n");
	else if (H->h_addrtype == AF_INET6)
		printf("IPv6\n");

	printf("Longitud en bytes de cada direción: %d\n", H->h_length);

	printf("IPs del host:\n");
	for (i = 0; H->h_addr_list[i] != NULL; i++) {
		IP.s_addr = *((uint32_t*)H->h_addr_list[i]);	// Conversión de punteros
		printf("\tIP[%d]: %s\n", i, inet_ntoa(IP));
	}

	printf("\n\n");
}

void getHostInfoByName(char *name) {
	struct hostent *Host;

	if (!(Host = gethostbyname(name))) {
		printf("Error\n");
		exit(1);
	}

	printHost(Host);
}

void getHostInfoByAdress(char *adress) {
	struct hostent *Host;
	uint32_t addr = inet_addr(adress);		// De textual a entero de 32

	if (!(Host = gethostbyaddr(&addr, sizeof(addr), AF_INET))) {
		printf("Error\n");
		exit(1);
	}

	printHost(Host);
}

void main() {
	struct hostent *Host, *Copia;
	int i, j;

	// 1
	printf("/-------------------Ejercicio 1-------------------/\n");
	getHostInfoByName("www.elpais.es");

	// 2
	printf("/-------------------Ejercicio 2-------------------/\n");
	getHostInfoByAdress("130.206.192.18");

	// 3
	printf("/-------------------Ejercicio 3-------------------/\n");
	if (!(Host = gethostbyname("www.youtube.es"))) {
		printf("Error\n"); exit(1);
	}

	// Sacando copia a un lugar seguro
	Copia = (struct hostent*)malloc(sizeof(struct hostent));
	memcpy(Copia, Host, sizeof(struct hostent));
	printHost(Copia);

	if (!(Host = gethostbyname("www.reddit.com"))) {
		printf("Error\n"); exit(1);
	} printHost(Copia);
	

	// 4
	printf("/-------------------Ejercicio 4-------------------/\n");
	if (!(Host = gethostbyname("www.xataka.com"))) {
		printf("Error\n"); exit(1);
	} printHost(Host);

	for (i = 0; Host->h_addr_list[i] != NULL; i++) ;	// Contador de la lista de IPs

	uint32_t *arrayDirecciones;
	arrayDirecciones = (uint32_t*)malloc(i*sizeof(uint32_t));		// Vector estático de direcciones (uint32_t)

	for (j = 0; j < i; j++)
		arrayDirecciones[j] = *((uint32_t*)Host->h_addr_list[j]);

	for (j = 0; j < i; j++) {
		if (!(Host = gethostbyaddr(arrayDirecciones + j, sizeof(uint32_t), AF_INET))) {		// Se obtiene info a partir de las direcciones
			printf("Error %d\n", j); exit(1);
		} printHost(Host);
	}

	// 5
	printf("/-------------------Ejercicio 5-------------------/\n");
	sethostent(1);		// Abre la base de datos del DNS

	while((Host = gethostent()) != NULL)	// Escanea la base de datos hasta que no haya más entradas
		printHost(Host);

	endhostent();		// Cierra la base de datos
}