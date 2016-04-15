#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>
#include<time.h>
int main()
{
	struct stat p;
	struct stat *ptr = &p;
	if(stat("c",ptr) < 0) {perror("Stat :"); exit(1);}
	printf("Is regular file? %d\n",S_ISREG(ptr->st_mode));
	printf("Is directory? %d\n",S_ISDIR(ptr->st_mode));
	printf("Is symbolic link? %d\n",S_ISLNK(ptr->st_mode));
	printf("Permissions: %lo\n",ptr->st_mode);
	printf("Size: %d\n",ptr->st_size);
	printf("Inode number: %d\n",ptr->st_ino);
	printf("Status change: %s\n",ctime(&ptr->st_ctime));
	printf("File access: %s\n",ctime(&ptr->st_atime));
	printf("File modification: %s\n",ctime(&ptr->st_mtime));
	printf("Hard links: %d\n",ptr->st_nlink);
	return 0;
}
