import googlemaps, sys, re
try:
    import urllib2
except:
    print('Run with python2')
    exit()
    
api_key = 'fot one key'
api_key = 'AIzaSyDhFZ_MRtG4Xy1IY5zZMUPnWsg_loYAPnM'

url='https://maps.googleapis.com/maps/api/geocode/json?address=__HOLDER__&key=%s' %api_key

gmaps=googlemaps.Client(api_key)

def distance(src,dest,waypoints=None):
	pooled_route = gmaps.directions(src,dest,waypoints=waypoints)[0]
	route = gmaps.directions(src, dest)[0]
	optimal_distance = float(route["legs"][0]["distance"]["text"].split()[0])
	pooled_legs = pooled_route["legs"]
	pooled_distance =0
	for i in pooled_legs:
		pooled_distance += float(i["distance"]["text"].split()[0])
	print src,dest,"Pooled distance",pooled_distance,"Optimal distance",optimal_distance

	
	
	
	
