#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define LONG_MENSAJE 100


int main(int argc, char const *argv[]) {
	int creacion, envio;
	struct sockaddr_in direccion;
	socklen_t tamano = sizeof(struct sockaddr_in);
	char mensaje[LONG_MENSAJE];
	uint16_t puerto = 14641;

	if (argc == 2 && atoi(argv[1]) > 5000) {
		puerto = atoi(argv[1]);
	}

	if ((creacion = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		perror("No se pudo crear socket");
		exit(-1);
	}

	direccion.sin_family = AF_INET;					// IPv4
	direccion.sin_port = htons(puerto);				// Número de puerto (de orden de host a orden de red)
	direccion.sin_addr.s_addr = inet_addr("127.0.0.1");

	if (connect(creacion, (struct sockaddr*) &direccion, tamano) < 0) {	// Asigna la dirección al socket
		perror("No se pudo conectar");
		close(creacion);
		exit(-1);
	}

	if ((envio = recv(creacion, mensaje, LONG_MENSAJE, 0)) < 0) {
		perror("No se pudo recibir el mensaje");
		exit(-1);
	}

	printf("Mensaje: %s\n", mensaje);
	printf("Bytes recibidos: %d\n", envio);

	close(creacion);
}