#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define MAX_STRING 20

int main(int argc, char const *argv[]) {
	int socketFD, bytes = 0;

	struct sockaddr_in propia, servidor;
	socklen_t tamano = sizeof(struct sockaddr_in);

	char mensaje[MAX_STRING];

    char ip[] = "127.0.0.1";
	uint16_t puerto2 = 35498;


	// Argumentos por línea de comandos
    if (argc > 1 && atoi(argv[1]) > 2000 && atoi(argv[1]) < 65000) puerto2 = atoi(argv[1]);
    if (argc > 2) strcpy(ip, argv[2]);


    // Creación del socket, IPv4 y sin conexión
    if ((socketFD = socket(PF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("socket");
        exit(EXIT_FAILURE);
    }


    // Llenando las estructura de la info
    propia.sin_family = AF_INET;              // IPv4
    propia.sin_port = htons(puerto2);      // Número de puerto (de orden de host a orden de red)
    propia.sin_addr.s_addr = inet_addr(ip);   // Dirección IPv4 (cualquiera, de orden de host a orden de red)ç

    // Asigna la dirección al socket
    if (bind(socketFD, (struct sockaddr*)&propia, tamano) < 0) {
        perror("bind");
        exit(EXIT_FAILURE);
    }

    
    while ((bytes = recvfrom(socketFD, mensaje, MAX_STRING, MSG_PEEK, (struct sockaddr*)&servidor, NULL)) > 0) {
        printf("Paquete recibido desde %s:%d\n", inet_ntoa(servidor.sin_addr), ntohs(servidor.sin_port));
        printf("\tMensaje: %s\n", mensaje);
        printf("\tBytes: %d\n", bytes);
    }

    /*  ¿Podrán recuperarse los datos restantes con una nueva sentencia recvfrom?

        For message-based sockets [...] the entire message shall be read in a single operation.
        If a message is too long to fit in the supplied buffer, and MSG_PEEK is not set in the
        flags argument, the excess bytes shall be DISCARDED.
    */

    if ((bytes = recvfrom(socketFD, mensaje, MAX_STRING, MSG_PEEK, (struct sockaddr*)&servidor, NULL)) < 0) {
        perror("recvfrom");
        exit(EXIT_FAILURE);
    }

    printf("Paquete recibido desde %s:%d\n", inet_ntoa(servidor.sin_addr), ntohs(servidor.sin_port));
    printf("\tMensaje: %s\n", mensaje);
    printf("\tBytes: %d\n", bytes);

    close(socketFD);

    return(EXIT_SUCCESS);
}