#include <stdio.h>

float suma(float a, float b)
{  float c=a+b;
   return(c); }

void imprime(int i)
{  printf("Numero: %f\n",i); }

int main()
{  float a=5, b=6, c;
   c=suma(a,b);
   imprime(c);
}
