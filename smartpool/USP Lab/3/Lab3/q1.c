#include<stdio.h>
#include<unistd.h>
#include<fcntl.h>
#include<dirent.h>
#include<sys/types.h>
#include<string.h>	

void printDirectory(DIR *);

int main(int argc,char *argv[])
{
	if(argc==1) //No arguments were given
	{
		//Print files in current Directory
		DIR *directory=opendir(".");
		printDirectory(directory);	
		return 0;
	}
	
	//Arguments were given, retrieve the path
	char* filepath=argv[1];
	DIR *directory=opendir(filepath);
	if(directory!=NULL)
	{
		//Display everything in directory
		printDirectory(directory);	
	}
	else
	{
		printf("Is a file\n");
		int fd=open(filepath,O_RDONLY);
		char *buf;
		printf("Reading Using READ : \n");
		while(read(fd,buf,1)!=0)//While more then 1 character is read
			printf("%s",buf);
		printf("\n");
	}
	return 0;
}

//Function to print the contents of a directory		
void printDirectory(DIR *directory)
{
	struct dirent *dir;
	int filecount=0,dircount=0;
	while((dir=readdir(directory))!=NULL)
	{
		if(strcmp(dir->d_name,".")==0 || strcmp(dir->d_name,"..")==0)
			continue;		
		printf("%s\n",dir->d_name);
		if(dir->d_type == DT_REG)
			filecount++;
		else
			dircount++;
	}
	printf("No. Of Files = %d\n",filecount);	
	printf("No. Of Subdirectories = %d\n",dircount);
}	
				
