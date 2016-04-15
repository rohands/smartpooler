#include<stdio.h>
#include<unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
       
int main()
{
	int fd = open("pipe",O_WRONLY);
	int fd1 = open("pipe1",O_RDONLY);
	while(1)
	{
		int x;
		scanf("%d",&x);
		write(fd,&x,sizeof(int));
		sleep(2);
		read(fd1,(void*)&x,sizeof(int));
		printf("sqr: %d\n",x);
	}
}
