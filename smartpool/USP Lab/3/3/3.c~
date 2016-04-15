#include<sys/types.h>
#include<dirent.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>
#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int main()
{
	DIR *dir;
	if((dir = opendir("a")) < 0) { perror("opendir"); exit(1); }
	struct stat s;
	struct dirent *d;
	while((d = readdir(dir)) != NULL)
	{
		char path[20] = "a/";
		strcat(path,d->d_name);
		printf("%s\n",path);
		stat(path , &s);
		if(s.st_size == 0)
		{
			rmdir(path);
		}
	}
	
	return 0;
}
