#include"proto.h"
#include<stdio.h>
void sort(struct student s[] , int n)
{
	int i;
	int j;
	struct student temp;
	for(i = 0; i<n ; i++)
	{
		for(j=0; j<n-i-1 ; j++)
		{
			if(total(s[j].marks , 5) < total(s[j+1].marks , 5))
			{
				temp = s[j];
				s[j] = s[j+1];
				s[j+1] = temp;
			}
		}
	}
	
	
	for(i=0;i<n;i++)
	{
		printf("%s ",s[i].name);
		for(j=0;j<5;j++)
			printf("%d ",s[i].marks[j]);
		printf("total : %d\n",total(s[i].marks , 5));
	}
}
