#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include<stdio.h>
#include<stdlib.h>

// fd to the file still exists in the process
// can still access the file in this process 

int main()
{
	int fd;
	if((fd=open("a.txt",O_RDONLY,0664)) < 0)
	{
		perror("open"); exit(1);
	}
	char s[5];
	if(read(fd,(void*)s,5) < 0)
	{
		perror("read"); exit(2);
	}
	s[5] = '\0';
	printf("%s\n",s);
	if(rename("a.txt","./dir1/b.txt") < 0)
	{
		perror("rename"); exit(3);
	}
	char p[5];
	if(read(fd,(void*)p,5) < 0)
	{
		perror("read"); exit(2);
	}
	printf("%s\n",p);
	return 0;
}
