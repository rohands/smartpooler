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
	
	int sqr;
	int cube;
	int pid;
	
	while(1)
	{
		msgrcv(id1 , (void*)&m1 , sizeof(m1)-sizeof(long), 0 , 0);
		
		if(strcmp(m1.cmd,"sqr") == 0)
		{
			sqr = m1.val * m1.val;
			m1.val = sqr;
		}
		if(strcmp(m1.cmd,"cube") == 0)
		{
			cube = m1.val * m1.val * m1.val;
			m1.val = cube;
		}
		
		msgsnd(id2 , (void*)&m1 , sizeof(m1)-sizeof(long), 0);	
	}
	return 0;
}
