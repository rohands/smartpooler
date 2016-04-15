#include<stdio.h>
#include<signal.h>
#include<unistd.h>
#include<sys/types.h>

void sig(int sig)
{
}

int main(int argc, char *argv[])
{
	signal(SIGUSR1, sig);
	int i,j,k;
	int count=0;

	pid_t m;
	m = fork();
	
	if(m != 0)
	{
		for(i = 100; i >= 20; i = i-20)
		{
            printf("PARENT: ");
			for(j=0;j<10;j++)
					printf("%d\t",i-j);
			
			printf("\n");
			sleep(1);
			kill(m,SIGUSR1);
			pause();
		}
	}
	else
	{
	    		
		for(i = 81; i > 0; i = i - 20)
        {
           pause();
           printf("CHILD : ");
           for(j=0;j<10;j++)
                 printf("%d\t",i+j);
              
		   printf("\n");
		   sleep(1);
		   kill(getppid(),SIGUSR1);	
		}
	}
}


