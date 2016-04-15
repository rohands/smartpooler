#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void sig_handler(int signo)
{
    printf("Alarm caught: pid = %d\n" , getpid());
}

main()
{
   signal(SIGALRM , sig_handler);
   alarm(1);
   pid_t m = fork();
   //alarm(1);
   
   if(m == 0)
   {
       printf("Child process: pid = %d\n" , getpid());
       while(1)
       { 
       }
         
   }
   else
   {
      printf("Parent process: pid = %d\n" , getpid());
      while(1)
       { 
       }
   }
}
