#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
	int pid;
	int fd[2];
	int fd1[2];
	pipe(fd);
	pid = fork();
	if(pid < 0)
	{
		perror("fork");
		exit(1);
	}
	else if(pid == 0)
	{
		//child1
		// writes output of ls in fd
		close(fd[0]);
		close(1);
		dup(fd[1]);
		close(fd[1]);
		execl("/bin/ls","ls",NULL);
	}
	else
	{
		//parent
		pipe(fd1);
		int pid1 = fork();
		if(pid1 < 0)
		{
			perror("fork");
			exit(1);
		}
		else if(pid1 == 0)
		{
			//child2
			// reads input from fd and writes to pipe fd1
			close(fd[1]);
			close(0);
			dup(fd[0]);
			close(fd[0]);
			
			close(fd1[0]);
			close(1);
			dup(fd1[1]);
			close(fd1[1]);
			
			execl("/bin/grep","grep","^p.*",NULL);
		}
		else
		{
			//parent
			int pid2 = fork();
			if(pid2 < 0)
			{
				perror("fork");
				exit(1);
			}
			else if(pid2 == 0)
			{
				//child3
				//reads input from fd1
				close(fd[0]);
				close(fd[1]);
				close(fd1[1]);
				close(0);
				dup(fd1[0]);
				close(fd1[0]);
				if(execl("/usr/bin/sort","sort","-r",NULL) < 0)
				{
					perror("exec");
					exit(1);
				}
			}
			else
			{
				//parent
				close(fd[0]);
				close(fd[1]);
				close(fd1[0]);
				close(fd1[1]);
				wait((int*)0);
				wait((int*)0);
				wait((int*)0);
			}
		}
	}
	return 0;
}
