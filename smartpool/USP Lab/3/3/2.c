#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>


int main()
{
	int fd = open("b.txt",O_RDONLY);
	char *buf;
	if((fd = read(fd , buf , 3)) < 0)
	{
		perror("read"); exit(1);
	}
	printf("%s\n",buf);
	
	if(readlink("b.txt" , buf , 7) < 0)
	{
		perror("readlink"); exit(2);
	}
	printf("%s\n",buf);
	return 0;
}
