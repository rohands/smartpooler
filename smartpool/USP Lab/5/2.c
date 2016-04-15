#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>

int main()
{
	setenv("X","10",1);
	int pid = fork();
	char *p;
	if(pid == 0)
	{
		//child
		p = getenv("X");
		if(p)
		{
			printf("In child: X = %s\n",p);
			strcat(p,"addedinchlid");
			printf("In child after change: X = %s\n",p);
			sleep(6);
			printf("In child after change in parent: X = %s\n",p);
		}
		else
		{
			perror("getenv");
			exit(2);
		}
	}
	else if(pid > 0)
	{
		//parent
		sleep(3);
		p = getenv("X");
		if(p)
		{
			printf("In parent: X = %s\n",p);
			strcat(p,"addedinparent");
			printf("In parent after change: X = %s\n",p);
		}
		else
		{
			perror("getenv");
			exit(2);
		}
	}
	else
	{
		perror("fork");
		exit(1);
	}
	return 0;
}
