#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <ctype.h>

#define MAX_STRING 100

int main(int argc, char const *argv[]) {
	int socketFD, bytes = 0, i;
	
    struct sockaddr_in servidor, cliente;
	socklen_t tamano = sizeof(struct sockaddr_in);
    socklen_t tamanoC = sizeof(cliente);
    
    char mensaje[MAX_STRING];
    int longitudMensaje;
	
    char ip[] = "127.0.0.1";
    uint16_t puerto1 = 14641;

    
    // Argumentos por línea de comandos
	if (argc > 1 && atoi(argv[1]) > 2000 && atoi(argv[1]) < 65000) puerto1 = atoi(argv[1]);
    if (argc > 2) strcpy(ip, argv[2]);

    
    // Creación del socket, IPv4 y sin conexión
    if ((socketFD = socket(PF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("socket");
        exit(EXIT_FAILURE);
    }


    // Llenando las estructura de la info
    servidor.sin_family = AF_INET;                  // IPv4
    servidor.sin_port = htons(puerto1);              // Número de puerto (de orden de host a orden de red)
    servidor.sin_addr.s_addr = htonl(INADDR_ANY);   // Dirección IPv4 (cualquiera, de orden de host a orden de red)

    // Asignando dirección y puerto al socket
    if (bind(socketFD, (struct sockaddr*)&servidor, tamano) < 0) {    // Asigna la dirección al socket
        perror("bind");
        close(socketFD);
        exit(EXIT_FAILURE);
    }

    while (1) {
        if ((bytes = recvfrom(socketFD, mensaje, MAX_STRING, 0, (struct sockaddr*)&cliente, &tamanoC)) < 0) {
            perror("recvfrom");
            exit(EXIT_FAILURE);
        }        
        longitudMensaje = strlen(mensaje) + 1;

        printf("Paquete recibido desde %s:%d\n", inet_ntoa(cliente.sin_addr), ntohs(cliente.sin_port));
        printf("\tMensaje: %s\n", mensaje);
        printf("\tBytes: %d\n", bytes);

        for(i = 0 ; i < longitudMensaje; i++)
            mensaje[i] = toupper(mensaje[i]);

        printf("Pasado a mayúsculas.\n");

        if ((bytes = sendto(socketFD, (char*)mensaje, longitudMensaje, 0, (struct sockaddr*)&cliente, tamano)) < 0) {    // Asigna la dirección al socket
            perror("sendto");
            exit(EXIT_FAILURE);
        }

        printf("Paquete enviado a %s:%d\n", inet_ntoa(cliente.sin_addr), ntohs(cliente.sin_port));
        printf("\tMensaje: %s\n", mensaje);
        printf("\tBytes: %d\n\n", bytes);   
    }

    close(socketFD);

    return(EXIT_SUCCESS);
}