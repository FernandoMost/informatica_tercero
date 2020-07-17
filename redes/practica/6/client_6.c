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
	int creacion, envio = 0, rec = 0;
	struct sockaddr_in direccion;
	socklen_t tamano = sizeof(struct sockaddr_in);
	char mensaje[LONG_MENSAJE], ip[] = "127.0.0.1";
	int longMensaje;
	uint16_t puerto = 14641;

	if (argc > 1 && atoi(argv[1]) > 5000) {
		puerto = atoi(argv[1]);
		if (argc > 2) {
			strcpy(ip, argv[2]);
		}
	}

	if ((creacion = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		perror("No se pudo crear socket");
		exit(-1);
	}

	direccion.sin_family = AF_INET;					// IPv4
	direccion.sin_port = htons(puerto);				// Número de puerto (de orden de host a orden de red)
	direccion.sin_addr.s_addr = inet_addr(ip);

	if (connect(creacion, (struct sockaddr*) &direccion, tamano) < 0) {	// Asigna la dirección al socket
		perror("No se pudo conectar");
		close(creacion);
		exit(-1);
	}

	printf("Conectado con %s : %d\n", inet_ntoa(direccion.sin_addr), htons(direccion.sin_port));

	while (1) {
		printf("\nIntroduzca mensaje: ");
		scanf(" %[^\r\n]", mensaje);
		if (!strcmp(mensaje, "exit")) exit(0);
		longMensaje = strlen(mensaje) + 1;

		if ((envio = send(creacion, mensaje, longMensaje, 0)) < 0) {
			perror("No se pudo enviar el mensaje");
			exit(-1);
		}

		printf("Mensaje enviado, bytes: %d\n", envio);

		if ((rec = recv(creacion, mensaje, LONG_MENSAJE, 0)) < 0) {
			perror("No se pudo enviar el mensaje");
			exit(-1);
		}

		printf("Mensaje recibido: %s\n", mensaje);
		printf("Bytes recibidos: %d\n", rec);
	}

	close(creacion);
}
