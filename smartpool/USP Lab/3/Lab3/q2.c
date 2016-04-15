#include<stdio.h>
#include<unistd.h>
#include<fcntl.h>

void main(){
	int fd=open("symlink2",O_RDONLY);
	char *buf;
	
	printf("Reading Using READ : \n");
	while(read(fd,buf,1)!=0)
		printf("%s",buf);
		
	printf("\nReading Using READLINK : \n");	
	readlink("symlink2",buf,500);
	printf("%s\n",buf);
}	
			
