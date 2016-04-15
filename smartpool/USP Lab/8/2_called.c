#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>


void handler_sig_int(int signo)
{
        printf("SIGINT caught in execed process(called)\n");
}

int main()
{
    //printf("hey");
	sigset_t blocked;
	signal(SIGINT,handler_sig_int);
	sigpending(&blocked);
	if(sigismember(&blocked,SIGINT))
      printf("SIGINT is pending even in execed process.\n");
    sigprocmask(SIG_UNBLOCK,&blocked,NULL);
    //printf("bye");
}
