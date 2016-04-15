#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

struct msgbuf
{
	long type;
	char cmd[5];
	int val;
};

int main()
{
	
	int id1 = msgget(ftok("/home/student/1PI1CS099/9",'A') , IPC_CREAT|0666);
	int id2 = msgget(ftok("/home/student/1PI1CS099/9",'B') , IPC_CREAT|0666);
	
	struct msgbuf m1;
	
	while(1)
	{
		scanf("%s",m1.cmd);
		if(strcmp(m1.cmd,"exit") == 0)
			exit(1);
		
		scanf("%d",&(m1.val));
		m1.type = getpid();
		
		msgsnd(id1 , (void*)&m1 , sizeof(m1)-sizeof(long), 0);
		
		msgrcv(id2 , (void*)&m1 , sizeof(m1)-sizeof(long), getpid() , 0);
		printf("Result: %d\n",m1.val);
			
	}
	return 0;
}
