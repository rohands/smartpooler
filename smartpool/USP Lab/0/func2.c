#include"proto.h"
struct ele func2(struct ele a , struct ele b)
{
	struct ele res;
	res.x = a.x + b.x;
	res.y = a.y + b.y;
	return res;
}
