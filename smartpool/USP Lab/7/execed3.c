#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

main()
{
   int fd;
   printf("In the execed process, id = %d\n" , getpid());
   if((fd = open("a.txt" , O_RDWR)) == -1)
      perror("Error");
   printf("File descriptor in the execed process: %d\n" , fd);
}


