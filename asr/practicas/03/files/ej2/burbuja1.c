#include <stdio.h>
#include <stdlib.h>
 
int fillrand(int A[],int N,int R){         int k;
         for(k=1;k<=N;k++){
                 A[k]=rand()%R;
             }     
         return 1;     }
 
int bubblesort(int A[],int N){
         int i,j,AUX;
         for(i=2;i<=N;i++){                 for(j=N;j>=i;j--){
                         if(A[j-1]>A[j]){
                                 AUX=A[j-1];
                                 A[j-1]=A[j];
                                 A[j]=AUX;                             }
                     }
             }
         return 1;
     } 

int salida(int A[],int N){
         int k;
         for(k=1;k<=N;k++){
                 printf("%d, ",A[k]);             }
	printf("\n");
         return 1;
     }
 
void main(){         int A[11];
         fillrand(A,10,100);
         printf("BUBBLE SORT\n");         printf("Datos a ordenar:\n");
         salida(A,10);
         printf("Datos ordenados:\n");
         bubblesort(A,10);
         salida(A,10);
     }
