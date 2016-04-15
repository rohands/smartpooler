#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>

int main()
{
	int pid;
	int c = 0;
	//int d = 0;
	pid = fork();
	if(pid == 0)
	{
		c = 97;
		while(c <= 122)
		{
			printf("%c\n", c);
			++c;
			sleep(rand() % 3);
		}
		
	}
	else
	{
		c = 65;
		while(c <= 90)
		{
			printf("%c\n", c);
			++c;
			sleep(rand() % 3);
		}
	}
    //printf("abcd\n");
	//fork();
	//printf("pqrs\n");
	return 0;
}
