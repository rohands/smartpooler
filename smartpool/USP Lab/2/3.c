#include<unistd.h>
#include<stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include<stdlib.h>

int main()
{
	int fd1;
	if((fd1=open("res.txt",O_WRONLY,0664))<0){perror("open");exit(1);}
	dup2(fd1,1);
	dup2(fd1,2);
	if((open("x.txt",O_WRONLY,0664))<0){perror("open");}
	printf("Hello\n");
	return 0;
}
