#include<stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include<sys/stat.h>
#include<fcntl.h>

int main()
{
	int fd = open("input.txt", O_RDONLY, 0664);
	int size_of_file = lseek(fd,0,SEEK_END);
	lseek(fd,0,SEEK_SET);
	size_of_file--;
	char s[size_of_file];
	int i;
	for(i = size_of_file-1 ; i>=0 ; i--)
	{
		read(fd, (void*)&(s[i]) , 1);
	}
	s[size_of_file] = '\0';
	printf("%s\n",s);
	close(fd);
	return 0;
}
