#include <unistd.h>
#include <stdio.h>

int main()
{
      fork();
	//printf("After fork1\n");
      fork() && fork() || fork();
	//printf("After a bunch of forks\n");
      fork();
      printf("test\n");
      return 0;
}
