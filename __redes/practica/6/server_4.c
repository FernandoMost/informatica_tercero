#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>


int main(int argc, char const *argv[]) {
	int creacion, conexion, envio1, envio2;
	struct sockaddr_in direccion, cliente;
	socklen_t tamano = sizeof(struct sockaddr_in);
	char mensaje1[] = "esta es la primera parte";
	char mensaje2[] = " y esta es la segunda\0";
	int longMensaje1 = strlen(mensaje1);
	int longMensaje2 = strlen(mensaje2) + 1;
	uint16_t puerto = 14641;

	if (argc == 2 && atoi(argv[1]) > 5000) {
		puerto = atoi(argv[1]);
	}

	if ((creacion = socket(PF_INET, SOCK_STREAM, 0)) < 0) {		// Creación del socket, IPv4 y orientado a conexión
		perror("No se pudo crear socket");
		exit(-1);
	}

	// llenar la estructura de 0's
	direccion.sin_family = AF_INET;					// IPv4
	direccion.sin_port = htons(puerto);				// Número de puerto (de orden de host a orden de red)
	direccion.sin_addr.s_addr = htonl(INADDR_ANY);	// Dirección IPv4 (cualquiera, de orden de host a orden de red)

	if (bind(creacion, (struct sockaddr*) &direccion, tamano) < 0) {	// Asigna la dirección al socket
		perror("No se pudo asignar el puerto al socket");
		close(creacion);
		exit(-1);
	}

	if (listen(creacion, 4) < 0) {					// El servidor se ponga a escuchar las conexiones (4, núm de solicitudes que pueden estar en la cola)
		perror("No se pudieron escuchar conexiones");
		exit(-1);
	}

	while (1) {
		if ((conexion = accept(creacion, (struct sockaddr*) &cliente, &tamano)) < 0) {		// solicitud de conexión por parte de un cliente, queremos que la acepte
			perror("No se pudo aceptar la solicitud");
			close(creacion);
			exit(-1);
		}

		printf("Conectado con %s:%d\n", inet_ntoa(cliente.sin_addr), htons(cliente.sin_port));

		if ((envio1 = send(conexion, mensaje1, longMensaje1, 0)) < 0 || (envio2 = send(conexion, mensaje2, longMensaje2, 0)) < 0) {
			perror("No se pudo enviar el mensaje");
			exit(-1);
		}

		printf("Bytes enviados: %d + %d = %d\n", envio1, envio2, envio1 + envio2);

		close(conexion);
	}

	close(creacion);
}



