from django.db import models


# Create your models here.
class RegUsers(models.Model):
	usn = models.CharField(primary_key=True,max_length=10)
	rating = models.IntegerField()
	phno = models.CharField(max_length=15)
	emailid = models.EmailField()

class ActivePoolers(models.Model):
	usn = models.ForeignKey('RegUsers')
	source = models.CharField(max_length=20)
	destination = models.CharField(max_length=20)
	src_lat = models.DecimalField(decimal_places=5,max_digits=10)
	src_lng = models.DecimalField(decimal_places=5,max_digits=10)
	dest_lat = models.DecimalField(decimal_places=5,max_digits=10)
	dest_lng = models.DecimalField(decimal_places=5,max_digits=10)
	start_datetime = models.DateTimeField()
	car_type = models.IntegerField()
	waypoints_usn = models.CharField(default="",max_length=10000)
	baggage = models.DecimalField(decimal_places=2,max_digits=10)
	price = models.DecimalField(decimal_places=1,max_digits=10)
	filled = models.IntegerField(default=0)
	filled_names = models.CharField(default="",max_length=10000000)
	class Meta:
		unique_together = (("usn","start_datetime"),)




