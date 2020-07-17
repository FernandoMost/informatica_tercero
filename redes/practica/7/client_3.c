#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define MAX_STRING 100

int main(int argc, char const *argv[]) {
	int socketFD, bytes = 0;

	struct sockaddr_in servidor, cliente;
	socklen_t tamano = sizeof(struct sockaddr_in), tamanoS;

	char mensaje[MAX_STRING];
    int longitudMensaje;

    char ip[] = "127.0.0.1";
	uint16_t puerto1 = 14641, puerto2 = 35498;


	// Argumentos por línea de comandos
    if (argc > 1 && atoi(argv[1]) > 2000 && atoi(argv[1]) < 65000) puerto1 = atoi(argv[1]);
    if (argc > 2 && atoi(argv[1]) > 2000 && atoi(argv[1]) < 65000) puerto2 = atoi(argv[2]);
    if (argc > 3) strcpy(ip, argv[3]);


    // Creación del socket, IPv4 y sin conexión
    if ((socketFD = socket(PF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("socket");
        exit(EXIT_FAILURE);
    }

    // Llenando las estructura de la info
    servidor.sin_family = AF_INET;                  // IPv4
    servidor.sin_port = htons(puerto2);          // Número de puerto (de orden de host a orden de red)
    servidor.sin_addr.s_addr = htonl(INADDR_ANY);   // Dirección IPv4 (cualquiera, de orden de host a orden de red)

    cliente.sin_family = AF_INET;                   // IPv4
    cliente.sin_port = htons(puerto1);               // Número de puerto (de orden de host a orden de red)
    cliente.sin_addr.s_addr = inet_addr(ip);        // Dirección IPv4 (cualquiera, de orden de host a orden de red)

    // Asigna la dirección al socket
    if (bind(socketFD, (struct sockaddr*)&cliente, tamano) < 0) {
        perror("bind");
        exit(EXIT_FAILURE);
    }

    while (strcmp(mensaje, "EXIT") != 0) {
        printf("Introduzca mensaje: ");
        scanf(" %[^\r\n]", mensaje);
        longitudMensaje = strlen(mensaje) + 1;

        if ((bytes = sendto(socketFD, (char*)mensaje, longitudMensaje, 0, (struct sockaddr*)&servidor, tamano)) < 0) {    // Asigna la dirección al socket
            perror("sendto");
            exit(EXIT_FAILURE);
        }

        printf("Paquete enviado a %s:%d\n", inet_ntoa(servidor.sin_addr), ntohs(servidor.sin_port));
        printf("\tMensaje: %s\n", mensaje);
        printf("\tBytes: %d\n", bytes);    

        if ((bytes = recvfrom(socketFD, mensaje, MAX_STRING, 0, (struct sockaddr*)&servidor, &tamanoS)) < 0) {
            perror("recvfrom");
            exit(EXIT_FAILURE);
        }

        printf("Paquete recibido desde %s:%d\n", inet_ntoa(servidor.sin_addr), ntohs(servidor.sin_port));
        printf("\tMensaje: %s\n", mensaje);
        printf("\tBytes: %d\n\n", bytes);
    }

    close(socketFD);

    return(EXIT_SUCCESS);
}