#include<unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include<stdlib.h>
#include<stdio.h>
int main()
{
	int fd;
	if((fd = open("abc.txt",O_RDWR|O_APPEND)) < 0)
	{
		perror("open");
		exit(1);
	}
	if(write(fd,"efgh",4) == -1)
	{
		perror("write");
		exit(2);
	}
	return 0;
}
