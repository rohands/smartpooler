#include <dirent.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
void main()
{
    char *folder="./hello"; //Path to folder
    DIR* path; 
    struct stat s;
    struct dirent *dir;
    path = opendir(folder); //Open the directory
    chdir(folder); 
    if(path == NULL)
		perror("Error:");
    else
    {
        while((dir=readdir(path))!=NULL)
        {
            stat(dir->d_name,&s);
            if(s.st_size==0)
                unlink(dir->d_name);
        }
        closedir(path);
    }
}
   
