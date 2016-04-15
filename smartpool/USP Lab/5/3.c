#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>

int main()
{
	int pid = fork();
	if(pid == 0)
	{
		//child
		sleep(5);
		printf("child: pid : %d ppid : %d\n",getpid(),getppid());
	}
	else if(pid > 0)
	{
		//parent
		//sleep(25);
		printf("parent: pid : %d ppid : %d\n",getpid(),getppid());
	}
	else
	{
		perror("fork");
		exit(1);
	}
	return 0;
}
