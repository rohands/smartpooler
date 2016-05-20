from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from .models import *
from django.db import IntegrityError
from django.contrib.auth import authenticate
from django.utils import timezone
from datetime import datetime
from . import temp
import geocoder
import time
import json
# Create your views here.

@csrf_exempt
def signup(request):
	params = request.POST
	usn = params.get("usn")
	password = params.get("password")
	phno = params.get("phno")
	rating = 1
	email = params.get("email")
	first_name = params.get("first_name")
	last_name  = params.get("last_name")
	try:
		user = User.objects.create_user(username=str(usn),password=str(password))
		user.first_name = first_name
		user.last_name = last_name
		user.email = email
		user.save()
		registered = RegUsers(usn=str(usn),rating=rating,phno=str(phno),emailid=str(email))
		registered.save()
	except IntegrityError as e:
		return HttpResponse("Username exists, please login")
	return HttpResponse("User successfully registered, please login with the credentials you provided")


def index(request):
	return HttpResponse("Hello")

@csrf_exempt
def login(request):
	params = request.POST
	usn = params.get("usn")
	password = params.get("password")
	try:
		user = User.objects.get(username=usn)
		user = authenticate(username=usn, password=password)
		if user is not None:
			request.session["logged_in"] = True
			request.session["usn"] = usn
			return HttpResponse("User logged in")
		else:
			return HttpResponse("Password is incorrect")		
	except:
		return HttpResponse("Username does not exist, please signup")

@csrf_exempt
def offer_ride(request):
	params = request.POST
	print params
	usn = params.get("usn")
	if usn != "0":
		user = RegUsers.objects.get(usn=usn)			
		source = params.get('source')
		destination = params.get('destination')
		g = geocoder.google(str(source))
		src_lat = g.latlng[0]
		src_lng = g.latlng[1]
		g = geocoder.google(str(destination))
		dest_lat = g.latlng[0]
		dest_lng = g.latlng[1]
		start_datetimestr = params.get("startDateTime")
		month_dict = {
						1 : 'Jan',
				        2 : 'Feb',
				        3 : 'Mar',
				        4 : 'Apr',
				        5 : 'May',
				        6 : 'Jun',
				        7 : 'Jul',
				        8 : 'Aug',
				        9 : 'Sep',
				        10 : 'Oct', 
				        11 : 'Nov', 
				        12 : 'Dec'}	
		intermediate_str = start_datetimestr[2:]
		x = month_dict[int(start_datetimestr[0])]
		start_datetime1 = x + ' ' + intermediate_str
		car_type = params.get("carType")
		if car_type == "SUV":
			car_type = 0
		else:
			car_type = 1
		print "CAR TYPE",car_type
		baggage = params.get("baggage")
		price = params.get("price")



		start_datetime = datetime.strptime(start_datetime1, '%b %d %Y %I:%M%p')
		new_object = ActivePoolers(usn=user,source=source,destination=destination,src_lat=src_lat,dest_lat=dest_lat,src_lng=src_lng,dest_lng=dest_lng,start_datetime=start_datetime,
			car_type=car_type,baggage=baggage,price=price)
		new_object.save()
		return HttpResponse("Ride successfully offered")
	else:
		return HttpResponse("Username invalid")

def logout(request):
	request.session["logged_in"] = False
	return HttpResponse("Logged out successfully")

@csrf_exempt
def join_ride(request):
	params = request.POST
	usn = params.get("usn")
	user = RegUsers.objects.get(usn=usn)
	user1 = User.objects.get(username=usn)			
	source = params.get('source')
	destination = params.get('destination')
	g = geocoder.google(str(source))
	src_lat = str(g.latlng[0])
	src_lng = str(g.latlng[1])
	g = geocoder.google(str(destination))
	dest_lat = str(g.latlng[0])
	dest_lng = str(g.latlng[1])
	start_datetimestr = params.get("startDateTime")
	month_dict = {
					1 : 'Jan',
			        2 : 'Feb',
			        3 : 'Mar',
			        4 : 'Apr',
			        5 : 'May',
			        6 : 'Jun',
			        7 : 'Jul',
			        8 : 'Aug',
			        9 : 'Sep',
			        10 : 'Oct', 
			        11 : 'Nov', 
			        12 : 'Dec'}	
	intermediate_str = start_datetimestr[2:]
	x = month_dict[int(start_datetimestr[0])]
	start_datetime1 = x + ' ' + intermediate_str
	baggage = params.get("baggage")
	start_datetime = datetime.strptime(start_datetime1, '%b %d %Y %I:%M%p')
	print params
	all_active = ActivePoolers.objects.all()
	deviations = list()
	for each_activepooler in all_active:
	 	pooler_src= str(each_activepooler.src_lat),str(each_activepooler.src_lng)
	 	pooler_dest = str(each_activepooler.dest_lat),str(each_activepooler.dest_lng)
	 	poolee_src = src_lat,src_lng 
	 	poolee_dest = dest_lat,dest_lng	 
	 	print "Lol"
	 	v = temp.distance(pooler_src,pooler_dest)
	 	if (v[0] - v[1]) < 50:
	 		deviations.append([each_activepooler,v])
	 	else:
	 		pass
	print deviations

	deviations = sorted(deviations,key=lambda x: x[1][0] - x[1][1])
	response_str = {}
	x = 0
	for each in deviations:
		x+=1
		d = dict()
		user1 = User.objects.get(username=each[0].usn.usn)
		d['fname'] = user1.first_name
		d['lname'] = user1.last_name
		d['source'] = each[0].source
		d['destination'] = each[0].destination
		
		d['distance'] = each[1][0]
		d['src_lat'] = str(each[0].src_lat)
		d['src_lng'] = str(each[0].src_lng)
		d['dest_lat'] = str(each[0].dest_lat)
		d['dest_lng'] = str(each[0].dest_lng)
		d['poolee_src_lat'] = str(src_lat)
		d['poolee_src_lng'] = str(src_lng)
		d['poolee_dest_lat'] = str(dest_lat)
		d['poolee_dest_lng'] = str(dest_lng)

		d['offer_usn'] = each[0].usn.usn
		response_str[x] = d	
	final = json.dumps(response_str)
	return HttpResponse(final )
@csrf_exempt
def mobile_apptest(request):
	params = request.POST
	time.sleep(5)
	return HttpResponse("POST DATA successful")


@csrf_exempt
def add_waypoints(request):
	params = request.POST
	usn = params.get("usn")
	musn = params.get("myUsn")
	print usn
	print musn
	user = RegUsers.objects.get(usn=usn)
	userobj = User.objects.get(username=musn)
	userobj1 = User.objects.get(username=usn)
	print userobj.first_name
	active = ActivePoolers.objects.get(usn=user)
	active.filled = active.filled + 1
	active.filled_names = active.filled_names + " " + userobj1.first_name
	names = active.filled_names
	Lame = str(names) + " " + str(active.filled)
	print Lame
	active.save()	
	return HttpResponse(str(Lame))