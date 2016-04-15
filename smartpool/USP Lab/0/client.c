#include<stdio.h>
#include"proto.h"
#include<string.h>

int main()
{
	struct student s[3];
	
	strcpy(s[0].name , "abc");
	s[0].marks[0] = 10;
	s[0].marks[1] = 50;
	s[0].marks[2] = 30;
	s[0].marks[3] = 40;
	s[0].marks[4] = 50;
	
	strcpy(s[1].name , "def");
	s[1].marks[0] = 20;
	s[1].marks[1] = 30;
	s[1].marks[2] = 90;
	s[1].marks[3] = 20;
	s[1].marks[4] = 60;

	strcpy(s[2].name , "ghi");
	s[2].marks[0] = 30;
	s[2].marks[1] = 40;
	s[2].marks[2] = 10;
	s[2].marks[3] = 60;
	s[2].marks[4] = 70;
	
	sort(s,3);
	return 0;
}
