#include <stdio.h>
#include <stdlib.h>

main()
{
   pid_t m , n;
   printf("Main process; id = %d\n" , getpid());
   m = fork();
   
   if(m == 0)
   {
      printf("Child1; id = %d\n" , getpid());
      n = fork();
      if(n == 0)   
      {
        printf("Child2; id = %d\n" , getpid());
        sleep(5);
      }
      else
      {
         sleep(2);
         exit(0);
      }
   }
   else
   {
        waitpid(m , NULL , 0);
        sleep(10);
   }
}
      
