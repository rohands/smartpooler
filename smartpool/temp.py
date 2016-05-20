import googlemaps, sys, re
import geocoder
try:
    import urllib2
except:
    print('Run with python2')
    exit()
    
api_key = 'AIzaSyCrhtfK-7AtsHzHUpm2njNke8TD4M3SpmM'

url='https://maps.googleapis.com/maps/api/geocode/json?address=__HOLDER__&key=%s' %api_key

gmaps=googlemaps.Client(api_key)

def distance(src,dest,waypoints=None):
	pooled_route = gmaps.directions(src,dest,waypoints=waypoints)[0]
	route = gmaps.directions(src, dest)[0]
	original_distance = float(route["legs"][0]["distance"]["text"].split()[0])
	pooled_legs = pooled_route["legs"]
	pooled_distance =0
	for i in pooled_legs:
		pooled_distance += float(i["distance"]["text"].split()[0])
	return pooled_distance,original_distance

	
	
	
	

