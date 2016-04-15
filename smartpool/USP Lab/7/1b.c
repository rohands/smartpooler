#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <errno.h>

void sig_handler(int signo)
{
    printf("Alarm caught: pid = %d\n" , getpid());
    printf("original\n");
}

main()
{
    signal(SIGALRM , sig_handler);
    printf("Parent executing: pid=%d\n" , getpid());
    alarm(5);
    if(execl("/home/pesit/1pi12cs177/Lab 7/exec" , "exec" , (char*)0) == -1)
        perror("Error");
}
