#include <stdio.h>
#include <signal.h>
#include <unistd.h>

main()
{
   struct sigaction sg;
   sigemptyset(&sg.sa_mask);
   sg.sa_flags = 0;
   sg.sa_handler = SIG_IGN;
   
   sigaction(SIGINT , &sg , 0);
   
   int count = 0;
   while(1)
   {
      if(count == 5)
      {
        sg.sa_handler = SIG_DFL;
        sigaction(SIGINT , &sg , 0);
      }
      sleep(2);
      count++;
  }
}
