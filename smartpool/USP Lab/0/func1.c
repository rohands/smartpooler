#include"proto.h"
int func1(struct ele a , struct ele b)
{
	if(a.x == b.x && a.y == b.y)
	{
		return 1;
	}
	return 0;
}
