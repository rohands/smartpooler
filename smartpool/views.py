from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from .models import *
from django.db import IntegrityError
from django.contrib.auth import authenticate
from django.utils import timezone
from . import temp
import time
# Create your views here.

@csrf_exempt
def signup(request):
	params = request.POST
	print params
	usn = params.get("usn")
	password = params.get("password")
	phno = params.get("phno")
	rating = 1
	email = params.get("email")
	first_name = params.get("first_name")
	last_name  = params.get("last_name")
	print usn
	try:
		user = User.objects.create_user(username=usn,password=password)
		user.first_name = first_name
		user.last_name = last_name
		user.email = email
		user.save()	
		registered = RegUsers(usn=usn,rating=rating,phno=phno,emailid=email)
		registered.save()
	except IntegrityError as e:
		return HttpResponse("Username exists, please login")
	return HttpResponse("User successfully registered")


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
		# print type(user)
		if user is not None:
			request.session["logged_in"] = True
			request.session["usn"] = usn
			return HttpResponse("Login success!")
		else:
			return HttpResponse("Password is incorrect")		
	except:
		return HttpResponse("Username does not exist, please signup")

@csrf_exempt
def offer_ride(request):
	params = request.POST
	if request.session.get("logged_in",False):
		usn = request.session.get("usn","0")
		if usn != "0":
			user = RegUsers.objects.get(usn=usn)			
			src_lat = params.get("src_lat")
			dest_lat = params.get("dest_lat")
			src_lng = params.get("src_lng")
			dest_lng = params.get("dest_lng")
			start_datetime = params.get("start_datetime")
			end_datetime = params.get("end_datetime")
			car_type = params.get("car_type")
			baggage = params.get("baggage")
			price = params.get("price")

			new_object = ActivePoolers(usn=user,src_lat=src_lat,dest_lat=dest_lat,src_lng=src_lng,dest_lng=dest_lng,start_datetime=timezone.now(),
				end_datetime=timezone.now(),car_type=car_type,baggage=baggage,price=price)

			new_object.save()
			return HttpResponse("Ride successfully offered")
		else:
			return HttpResponse("Username invalid")
	else:
		return HttpResponse("Please log in to offer ride")

def logout(request):
	request.session["logged_in"] = False
	del request.session["usn"] 
	return HttpResponse("Logged out successfully")

@csrf_exempt
def join_ride(request):
	if request.session.get("logged_in",False):
		usn = request.session.get("usn","0")
		if usn != "0":
			params = request.POST
			src_lat = params.get("src_lat")
			dest_lat = params.get("dest_lat")
			src_lng = params.get("src_lng")
			dest_lng = params.get("dest_lng")
			start_datetime = params.get("start_datetime")
			end_datetime = params.get("end_datetime")
			car_type = params.get("car_type")
			baggage = params.get("baggage")
			all_active = ActivePoolers.objects.all()
			deviations = list()
			for each_activepooler in all_active:
			 	pooler_src= each_activepooler.src_lat,each_activepooler.src_lng
			 	
			 	pooler_dest = each_activepooler.dest_lat,each_activepooler.dest_lng
			 	
			 	poolee_src = src_lat,src_lng 
			 	poolee_dest = dest_lat,dest_lng
			 	
			 	deviations.append([each_activepooler,temp.distance(pooler_src,pooler_dest,[poolee_src,poolee_dest])])
			mini = deviations[0]
			for each in deviations:
			 	if each[1] < mini[1] : 
			 		mini = each
	return HttpResponse("Ride join successful")
			
@csrf_exempt
def mobile_apptest(request):
	params = request.POST
	print params
	time.sleep(5)
	return HttpResponse("POST DATA successful")