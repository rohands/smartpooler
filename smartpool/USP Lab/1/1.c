#include<stdio.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>

int main()
{
	if(open("a.txt",O_CREAT|O_EXCL , 0664) == -1)
	{
		perror("Error ");
	}
	return 0;
}
