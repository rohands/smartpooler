#include<stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include<sys/stat.h>
#include<fcntl.h>

int main()
{
	int fd = open("input.txt", O_RDWR, 0664);
	printf("Lseek before the file: %d\n",lseek(fd,-2,SEEK_SET));
	

	printf("Lseek after the end of file: %d\n",lseek(fd,10,SEEK_END));
	write(fd , "hello" , 5); 
	
	close(fd);
	
	return 0;
}
