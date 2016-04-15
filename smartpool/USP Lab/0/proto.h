/* structure student with name and marks of five subjects
	create array of students and sort based on decending order of total marks
*/
struct student
{
	char name[20];
	int marks[5];
};
void sort(struct student s[], int n);
int total(int a[], int n);
