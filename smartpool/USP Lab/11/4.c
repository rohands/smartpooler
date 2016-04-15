#include<stdio.h>
#include<unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
       
int main()
{
	int fd = open("pipe",O_RDONLY);
	int fd1 = open("pipe1",O_WRONLY);
	int x;
	int res;
	while(1)
	{
		read(fd,(void*)&x,sizeof(int));
		res = x*x;
		write(fd1,&res,sizeof(int));
	}
}
