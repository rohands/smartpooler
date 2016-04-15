#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>

int main()
{
	char names[50][50];
	int sizes[50];
	int count = 0;

	DIR * pdir;
	struct dirent *p;
	int fd;
	int fd1;
	int size;
	int i;
	char ch;
	char s[20];
	if((fd1 = open("res.txt",O_CREAT|O_RDWR,0664)) < 0)
	{
		perror("open"); exit(2);
	}
	if((pdir = opendir("./dir1")) == NULL)
	{
		perror("opendir"); exit(1);
	}
	while((p = readdir(pdir)) != NULL)
	{
		printf("%s\n",p->d_name);
		strcpy(names[count],p->d_name);
		strcpy(s , "./dir1/");
		strcat(s,p->d_name);		
		fd = open(s,O_RDWR,0664);
		size = lseek(fd , 0, SEEK_END);
		sizes[count] = size;
		count++;
		lseek(fd , 0, SEEK_SET);
		for(i=0;i<size;i++)
		{
			read(fd,(void*)&ch,1);
			write(fd1,&ch,1);
		}
		close(fd);
	}
	printf("\n");
	int j;
	lseek(fd1,0,SEEK_SET);
	for(i=0;i<count;i++)
	{
		strcpy(s,"./dir2/");
		strcat(s,names[i]);
		fd = open(s , O_CREAT|O_RDWR , 0664);
		size = sizes[i];
		printf("%s %d\n",s,size);
		for(j=0;j<size;j++)
		{
			read(fd1,(void*)&ch,1);
			write(fd,&ch,1);
		}
		close(fd);
	}
}
