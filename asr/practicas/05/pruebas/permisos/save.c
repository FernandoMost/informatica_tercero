#include <stdio.h>

int main()
{  int i, j, k;
   for(i=0;i<=7;i++) for(j=0;j<=7;j++) for(k=0;k<=7;k++)
   {  printf("touch fich%d%d%d.txt\n",i,j,k);
      printf("chmod %d%d%d fich%d%d%d.txt\n",i,j,k,i,j,k); }
}
