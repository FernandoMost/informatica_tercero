#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

int main(int argc, char const *argv[]) {
	int socketFD, bytes = 0, i;
	
    struct sockaddr_in servidor, cliente;
	socklen_t tamano = sizeof(struct sockaddr_in);
    
    float array[] = {7.12, 78.6217, 2.7712, 792.742, 9274.148, 71.7, 792.742, 9274.148, 71.7};
    int bytesArray = sizeof(array);
    int tamanoArray = bytesArray / sizeof(float);
	
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
    servidor.sin_port = htons(puerto1);              // Número de puerto (de orden de host a orden de red)
    servidor.sin_addr.s_addr = htonl(INADDR_ANY);   // Dirección IPv4 (cualquiera, de orden de host a orden de red)

    cliente.sin_family = AF_INET;                   // IPv4
    cliente.sin_port = htons(puerto2);           // Número de puerto (de orden de host a orden de red)
    cliente.sin_addr.s_addr = inet_addr(ip);        // Dirección IPv4 (cualquiera, de orden de host a orden de red)


    // Asignando dirección y puerto al socket
    if (bind(socketFD, (struct sockaddr*)&servidor, tamano) < 0) {    // Asigna la dirección al socket
        perror("bind");
        close(socketFD);
        exit(EXIT_FAILURE);
    }
    
    if ((bytes = sendto(socketFD, (int*)&tamanoArray, sizeof(tamanoArray), 0, (struct sockaddr*)&cliente, tamano)) < 0) {    // Asigna la dirección al socket
        perror("sendto");
        close(socketFD);
        exit(EXIT_FAILURE);
    }

    if ((bytes = sendto(socketFD, (float*)array, bytesArray, 0, (struct sockaddr*)&cliente, tamano)) < 0) {    // Asigna la dirección al socket
        perror("sendto");
        close(socketFD);
        exit(EXIT_FAILURE);
    }

    printf("Array enviado: [ ");
    for (i = 0; i < tamanoArray; i++) printf("%f ", array[i]);
    printf("]\n");
    printf("Bytes: %d\n", bytes);

    close(socketFD);

    return(EXIT_SUCCESS);
}