#include <stdio.h>

float suma(float a, float b)
{  float c=a+b;
   return(c); }

float resta(float a, float b)
{  float c=a-b;
   return(c); }

float multiplica(float a, float b)
{  float c=a*b;
   return(c); }

#include <stdlib.h>

void imprime(float i)
{  printf("Numero: %f\n",i); }

int main()
{  float a=5, b=6, c;
   c=suma(a,b);
   imprime(c);
}

