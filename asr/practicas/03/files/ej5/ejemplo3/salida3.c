#include<stdio.h>

#include "float/suma.c"

int suma_int(int a, int b)
{  int c;

   // ahora sumamos
   c=a+b;
   return(c);
}

#include "float/resta.c"

#include<math.h>

#include "float/multiplica.c"

#include "void/imprime1.c"

#include <stdlib.h>
	
#include "void/imprime2.c"

#include "void/imprime_int.c"

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
