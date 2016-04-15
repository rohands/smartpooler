#include<signal.h>
#include<stdio.h>
#include<unistd.h>

int main()
{
	int pid;
	signal(SIGCHLD,SIG_IGN);
	
	pid = fork();
	if(pid == 0)
	{
		//child
	}
	else
	{
		//parent
		sleep(1000);
	}
	return 0;
}
