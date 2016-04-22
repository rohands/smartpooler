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
	return HttpResponse("User successfulcessfully registered")


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

		end_datetimestr = params.get("endDateTime")
		intermediate_str = end_datetimestr[2:]
		x = month_dict[int(end_datetimestr[0])]
		end_datetime1 = x + ' ' + intermediate_str

		car_type = params.get("carType")
		baggage = params.get("baggage")
		price = params.get("price")



		start_datetime = datetime.strptime(start_datetime1, '%b %d %Y %I:%M%p')
		end_datetime = datetime.strptime(end_datetime1, '%b %d %Y %I:%M%p')
		print src_lat,dest_lat,start_datetime

		new_object = ActivePoolers(usn=user,source=source,destination=destination,src_lat=src_lat,dest_lat=dest_lat,src_lng=src_lng,dest_lng=dest_lng,start_datetime=start_datetime,
			end_datetime=end_datetime,car_type=car_type,baggage=baggage,price=price)

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

	end_datetimestr = params.get("endDateTime")
	intermediate_str = end_datetimestr[2:]
	x = month_dict[int(end_datetimestr[0])]
	end_datetime1 = x + ' ' + intermediate_str
	baggage = params.get("baggage")
	start_datetime = datetime.strptime(start_datetime1, '%b %d %Y %I:%M%p')
	end_datetime = datetime.strptime(end_datetime1, '%b %d %Y %I:%M%p')
	# print src_lat,src_lng,dest_lng,dest_lat,start_datetime,end_datetime
	
	all_active = ActivePoolers.objects.all()
	deviations = list()
	for each_activepooler in all_active:
	 	pooler_src= str(each_activepooler.src_lat),str(each_activepooler.src_lng)
	 	pooler_dest = str(each_activepooler.dest_lat),str(each_activepooler.dest_lng)
	 	poolee_src = src_lat,src_lng 
	 	poolee_dest = dest_lat,dest_lng

	 	
	 	q = each_activepooler.waypoints.split(";")
	 	print q
	 	print each_activepooler.waypoints


	 	if len(q) != 1 :

	 		for i in range(len(q)):
	 			if q[i] == '':
	 				del q[i]
	 			q[i] = eval(q[i])
	 		q.append(poolee_src)
	 		q.append(poolee_dest)
	 	else:
	 		q = [poolee_src,poolee_dest]	 	

	 	v = temp.distance(pooler_src,pooler_dest,q)
	 	if (v[0] - v[1]) < 50:

	 		deviations.append([each_activepooler,v])
	 	else:
	 		pass

	print deviations

	# mini = deviations[0]
	# for each in deviations:
	# #  	if each[1] < mini[1] : 
	# #  		mini = each

	deviations = sorted(deviations,key=lambda x: x[1][0] - x[1][1])
	response_str = {}
	x = 0
	for each in deviations:
		x+=1
		d = dict()
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

		d['offer_usn'] = str(user1.username)
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
	user = RegUsers.objects.get(usn=usn)
	active = ActivePoolers.objects.get(usn=user)
	active.waypoints = active.waypoints +  "(" + params.get('src_lat') + "," + params.get('src_lng') + ")"+";" + "("+ params.get('dest_lat') + "," + params.get('dest_lng') + ");"
	active.save()	
	return HttpResponse("POST DATA successful")



# if request.session.get("logged_in",False):
	# 	usn = request.session.get("usn","0")
	# 	if usn != "0":
	# 		params = request.POST
	# 		src_lat = params.get("src_lat")
	# 		dest_lat = params.get("dest_lat")
	# 		src_lng = params.get("src_lng")
	# 		dest_lng = params.get("dest_lng")
			
	# 		start_datetime = params.get("start_datetime")
	# 		end_datetime = params.get("end_datetime")
			
	# 		baggage = params.get("baggage")
	# 		all_active = ActivePoolers.objects.all()
	# 		deviations = list()
	# 		for each_activepooler in all_active:
	# 		 	pooler_src= each_activepooler.src_lat,each_activepooler.src_lng
			 	
	# 		 	pooler_dest = each_activepooler.dest_lat,each_activepooler.dest_lng
			 	
	# 		 	poolee_src = src_lat,src_lng 
	# 		 	poolee_dest = dest_lat,dest_lng
			 	
	# 		 	deviations.append([each_activepooler,temp.distance(pooler_src,pooler_dest,[poolee_src,poolee_dest])])
	# 		mini = deviations[0]
	# 		for each in deviations:
	# 		 	if each[1] < mini[1] : 
	# 		 		mini = each
	# return HttpResponse("Ride join successful")