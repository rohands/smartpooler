#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void sig_handler(int signo)
{
   printf("Caught sigint.\n");
}

main()
{
    
    signal(SIGINT , sig_handler);
    int count = 0;
    while(1)
    {
        if(count == 5)
           signal(SIGINT , SIG_DFL);  
        sleep(2); 
        count++;
    }
}
