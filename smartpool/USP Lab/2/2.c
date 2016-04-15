#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>

int main()
{
	int fd1;
	int fd2;
	if((fd1=open("a.txt",O_RDWR,0664))<0){perror("open: ");exit(1);}
	if((fd2 = dup(fd1))<0){perror("dup: ");exit(2);}
	if((close(fd1))<0){perror("close: ");exit(3);}
	char s[10];
	if((read(fd2,(void*)s,7))<0){perror("read: ");exit(4);}
	printf("fd2(a.txt) : %s\n",s);
	
	if((fd1=open("b.txt",O_RDWR,0664))<0){perror("open: ");exit(1);}
	if((dup2(fd1,fd2))<0){perror("dup2: ");exit(2);}
	if((read(fd2,(void*)s,7))<0){perror("read: ");exit(4);}
	printf("fd2(b.txt) : %s\n",s);
	
	if((fd2 = fcntl(fd1,F_DUPFD,10)) < 0){perror("fntl: ");exit(5);}
	if((read(fd2,(void*)s,7))<0){perror("read: ");exit(4);}
	printf("fd2(b.txt) : %s\n",s);
	return 0;
}
