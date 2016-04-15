#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include<stdio.h>
#include<stdlib.h>

// unlink removes the name from the directory
// access to the file is still there from the file descriptor
// If  the  name  was the last link to a file but any processes still have the file open the file will remain in existence  until  the //
// last  file descriptor referring to it is closed.
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
	if(unlink("a.txt") < 0)
	{
		perror("unlink"); exit(3);
	}
	char p[5];
	if(read(fd,(void*)p,5) < 0)
	{
		perror("read"); exit(2);
	}
	printf("%s\n",p);
	return 0;
}
