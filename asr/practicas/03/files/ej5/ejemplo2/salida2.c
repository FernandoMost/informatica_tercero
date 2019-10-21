#include <stdio.h>

#include "float/suma.c"

#include "float/resta.c"

#include "float/multiplica.c"

#include <stdlib.h>

#include "void/imprime.c"

int main()
{  float a=5, b=6, c;
   c=suma(a,b);
   imprime(c);
}

