#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>

void handler_sig_int(int signo)
{
	printf("SIGINT caught in caller\n");
}

int main()
{
	sigset_t block;
	sigemptyset(&block);
	sigaddset(&block,SIGINT);
	signal(SIGINT,handler_sig_int);
	sigprocmask(SIG_BLOCK,&block,NULL);
	kill(getpid(),SIGINT);
	if( execl("./execed","execed",(char*) NULL) == -1)
	    perror("Error");
}
