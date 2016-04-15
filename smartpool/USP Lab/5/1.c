#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int main(int argc, char * argv[])
{
	if(execvp(argv[1] , &argv[1]) < 0 )
	{
		perror("execvp");
		exit(1);
	}
}
