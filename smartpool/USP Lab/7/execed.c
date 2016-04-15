#include <stdio.h>
#include <signal.h>

void sig_handler(int signo)
{
    printf("Alarm caught: pid = %d\n" , getpid());
}

main()
{
    printf("process id of exec-ed process:%d\n" , getpid());
    printf("hello. I am executing in the exec-ed process.\n");
     signal(SIGALRM , sig_handler);
    while(1)
    {
    }
}


