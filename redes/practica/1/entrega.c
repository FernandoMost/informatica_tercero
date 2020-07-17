#include <stdio.h>

#include <netinet/in.h>
#include <arpa/inet.h>

void main(){
	int i;

	// 1
	uint8_t ip[] = {193, 110, 128, 200};
	uint32_t *a;
	a = (uint32_t*) ip;			// cast
	printf("uint32_t = %u\n\n",*a);


	// 2
	uint8_t *b;
	b = (uint8_t*) a;			// cast contrario

	printf("uint8_t[] = ");
	for (i = 0; i < 4; i++)
		printf("%d.", *(b+i));
	printf("\n\n");


	// 3
	char ipText[] = "193.110.128.200";
	struct in_addr estructura1;

	inet_pton(AF_INET, ipText, &estructura1);
	printf("uint32_t con inet_pton = %u\n",estructura1.s_addr);
	
	inet_aton(ipText, &estructura1);
	printf("uint32_t con inet_aton = %u\n",estructura1.s_addr);
	
	*a = inet_addr(ipText);		// reutilizado del ej 1
	if (*a != INADDR_NONE)		// comprobación éxito
		printf("uint32_t con inet_addr = %u\n\n", *a);


	// 4
	char ipNuestra[] = "172.25.45.75", text[INET_ADDRSTRLEN];

	for (i = 0; i < 5; i++) {
		if (i == 0) inet_pton(AF_INET, ipNuestra, &estructura1);
		if (i == 1) estructura1.s_addr = INADDR_LOOPBACK;
		if (i == 2) estructura1.s_addr = INADDR_ANY;
		if (i == 3) estructura1.s_addr = INADDR_BROADCAST;
		if (i == 4) estructura1.s_addr = INADDR_NONE;

		inet_ntop(AF_INET, &estructura1, text, INET_ADDRSTRLEN);
		printf("%s\n", text);
	} printf("\n");


	// 6
	char ipA[] = "45.64.156.84", ipB[] = "185.51.53.68", ipC[] = "211.184.18.36", textual[INET_ADDRSTRLEN];
	struct in_addr str1, str2;

	for (i = 0; i < 3; i++){
		if (i == 0) str1.s_addr = inet_addr(ipA);
		else if (i == 1) str1.s_addr = inet_addr(ipB);
		else if (i == 2) str1.s_addr = inet_addr(ipC);

		str2.s_addr = inet_lnaof(str1);
		inet_ntop(AF_INET, &str2, textual, INET_ADDRSTRLEN);
		printf("Host: %s\n", textual);
		str2.s_addr = inet_netof(str1);
		inet_ntop(AF_INET, &str2, textual, INET_ADDRSTRLEN);
		printf("Red: %s\n", textual);
	}


	// 5
	printf("\nPuntero a buffer estático\n");
	inet_pton(AF_INET, ipText, &str1);
	printf("%p\n", inet_ntoa(str1));

	inet_pton(AF_INET, ipNuestra, &str1);
	printf("%p\n", inet_ntoa(str1));

	inet_pton(AF_INET, ipA, &str1);
	printf("%p\n", inet_ntoa(str1));

	inet_pton(AF_INET, ipB, &str1);
	printf("%p\n", inet_ntoa(str1));

	inet_pton(AF_INET, ipC, &str1);
	printf("%p\n", inet_ntoa(str1));


	// 7
	str2 = inet_makeaddr(inet_netof(str1), inet_lnaof(str1));
	inet_ntop(AF_INET, &str2, textual, INET_ADDRSTRLEN);
	printf("\nIP a partir de host y red: %s\n\n", textual);


	// 8
	sprintf(textual, "%s", inet_ntoa(str2));	// del uint32_t de la struct in_addr, a textual, y a string con sprintf
	if(inet_pton(AF_INET, textual, &str1) == 1) printf("uint32_t = %u\n\n", str1.s_addr);	// de nuevo al formato binario


	// 9
	char ip6cadena[] = "1080:0:0:0:8:800:200C:417A";
	struct in6_addr ipSeis1, ipSeis2 = IN6ADDR_LOOPBACK_INIT, ipSeis3 = IN6ADDR_ANY_INIT;

	printf("En hexadecimal:\n");
	if (inet_pton(AF_INET6, ip6cadena, &ipSeis1) == 1)
		for (i = 0; i < 8; i++)
			printf("%x ", ipSeis1.s6_addr16[i]);		// Puntero a uint8_t
	printf("\n");

	for (i = 0; i < 8; i++)
		printf("%x ", ipSeis2.s6_addr16[i]);
	printf("\n");

	for (i = 0; i < 8; i++)
		printf("%x ", ipSeis3.s6_addr16[i]);
	printf("\n\n");


	// 10
	char ipTextual[INET6_ADDRSTRLEN];

	printf("A textual:\n");
	inet_ntop(AF_INET6, &ipSeis1, ipTextual, INET6_ADDRSTRLEN);
	printf("%s\n", ipTextual);

	inet_ntop(AF_INET6, &ipSeis2, ipTextual, INET6_ADDRSTRLEN);
	printf("%s\n", ipTextual);

	inet_ntop(AF_INET6, &ipSeis3, ipTextual, INET6_ADDRSTRLEN);
	printf("%s\n", ipTextual);	
}
