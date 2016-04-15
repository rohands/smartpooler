#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
	int fd[2];
	int fd1[2];
	
	pipe(fd);
	pipe(fd1);
	
	int pid = fork();
	
	char res[20];
	char s[20];
	int m;
	
	if(pid < 0)
	{
		perror("fork");
		exit(1);
	}
	else if(pid > 0)
	{
		close(fd[1]);
		close(fd1[0]);
		//parent
		while(1)
		{
			m = read(fd[0] , res , 20);
			res[m] = '\0';
			
			printf("Server: Received String : %s\n",res);
			res[0] -= 32;  
			write(fd1[1] , res, strlen(res));
		}
	}
	else
	{
		//child
		close(fd[0]); 
		close(fd1[1]);
		while(1)
		{
			scanf("%s",s);	
			if(strcmp(s,"exit") != 0)
			{
				write(fd[1] , s, strlen(s));
				read(fd1[0] , s, strlen(s));
				printf("Client: Received String : %s\n",s);
			}
			else
			{
				exit(1);
			}
		}
	}
	return 0;
}
