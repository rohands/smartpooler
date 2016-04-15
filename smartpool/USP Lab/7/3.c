#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

main()
{
   int fd;
   printf("In the main process before execing, id = %d\n" , getpid());
   if((fd = open("a.txt" , O_RDWR)) == -1)
      perror("Error");
   printf("File descriptor in main process before execing: %d\n " , fd);
   if((fcntl(fd , F_SETFD , FD_CLOEXEC)) == -1)
      perror("Error"); 
   if(execl("/home/pesit/1pi12cs177/Lab 7/exec3" , "exec3" , (char*)0) == -1)
      perror("Error"); 
}
   
