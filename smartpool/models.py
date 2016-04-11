from django.db import models


# Create your models here.
class RegUsers(models.Model):
	usn = models.CharField(primary_key=True,max_length=10)
	rating = models.IntegerField()
	phno = models.CharField(max_length=15)
	emailid = models.EmailField()

class ActivePoolers(models.Model):
	usn = models.ForeignKey('RegUsers')
	src = models.DecimalField(decimal_places=5,max_digits=10)
	dest = models.DecimalField(decimal_places=5,max_digits=10)
	start_datetime = models.DateTimeField()
	end_datetime = models.DateTimeField()
	# 0 for Sedan, 1 for SUV
	car_type = models.IntegerField()
	baggage = models.DecimalField(decimal_places=2,max_digits=10)
	price = models.DecimalField(decimal_places=1,max_digits=10)

	class Meta:
		unique_together = (("usn","start_datetime"),)




