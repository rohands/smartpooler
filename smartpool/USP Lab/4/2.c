#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
int main()
{
    printf("abcd\n");
	fork();
	printf("pqrs\n");
	return 0;
}
