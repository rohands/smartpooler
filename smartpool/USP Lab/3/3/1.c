#include <sys/types.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

DIR * dir;
int file_count = 0;
int dir_count = 0;
struct dirent *d;

int main(int argc, char *argv[])
{
	if(argc == 1)
	{
		dir = opendir(".");
		while((d=readdir(dir)) != NULL)
		{
			if(strcmp(d->d_name,".") != 0 && strcmp(d->d_name,"..") != 0)
			{
				printf("%s\n",d->d_name);
				if(d->d_type == DT_DIR)
				{
					dir_count++;
				}
				else
				{
					file_count++;
				}
			}
		}
		printf("File count : %d\n",file_count);
		printf("Dir count : %d\n",dir_count);		
	}
	else
	{
		dir = opendir(argv[1]);
		if(dir != NULL)
		{
			while((d=readdir(dir)) != NULL)
			{
				if(strcmp(d->d_name,".") != 0 && strcmp(d->d_name,"..") != 0)
				{
					printf("%s\n",d->d_name);
					if(d->d_type == DT_DIR)
					{
						dir_count++;
					}
					else
					{
						file_count++;
					}
				}
			}
			printf("File count : %d\n",file_count);
			printf("Dir count : %d\n",dir_count);	
		}
		else
		{
			int fd = open(argv[1] , O_RDONLY);
			char *buf;
			while( read(fd , buf , 1) != 0)
			{
				printf("%s",buf);
			}
			printf("\n");
		}
	}
}
