#include<stdio.h>

float suma(float a, float b)
{  float c;
   // ahora sumamos
   c=a+b;

   return(c);
}

int suma_int(int a, int b)
{  int c;

   // ahora sumamos
   c=a+b;
   return(c);
}

float resta(float a, float b)
{  float c;

   // ahora restamos
   c=a-b;
   return(c);
}

#include<math.h>

float multiplica(float a, float b)
{  float c;
   // ahora multiplicamos
   c=a*b;
   return(c); }
  
void imprime1(float i)
{  printf("Numero: %e\n",i);
}


#include <stdlib.h>
	
void imprime2(float i)
{  printf("Numero: %f\n",i);
}


void imprime_int(int i)
{  printf("Numero: %d\n",i);
}


int main()
{  float a=5, b=6, c;
   int d;
   c=suma(a,b);
   c=multiplica(c,a);
   imprime1(c);
   imprime2(c);
   d=suma_int(5,6);
   imprime_int(d);
}
