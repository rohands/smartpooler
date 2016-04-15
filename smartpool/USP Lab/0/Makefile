cc=gcc
output:	client.o sort.o total.o
	$(cc) -o output client.o sort.o total.o
client.o:	client.c
	$(cc) -c client.c
sort.o:	sort.c
	$(cc) -c sort.c
total.o:	total.c
	$(cc) -c total.c
clean:
	rm -f output *.o *~
