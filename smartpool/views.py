from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from .models import *
from django.db import IntegrityError
from django.contrib.auth import authenticate
from django.utils import timezone
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
			src = params.get("src")
			dest = params.get("dest")
			start_datetime = params.get("start_datetime")
			end_datetime = params.get("end_datetime")
			car_type = params.get("car_type")
			baggage = params.get("baggage")
			price = params.get("price")

			new_object = ActivePoolers(usn=user,src=src,dest=dest,start_datetime=timezone.now(),
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

def join_ride(request):
	#To do