from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^signup/$', views.signup, name='signup'),
    url(r'^$', views.index, name='index'),
    url(r'^login/$', views.login, name='login'),
    url(r'^logout/$', views.logout, name='logout'),
    url(r'^offer_ride/$', views.offer_ride, name='offer_ride'),    
    url(r'^join_ride/$', views.join_ride, name='join_ride'),
    url(r'^mobile_apptest/$', views.mobile_apptest, name='mobile_apptest'),
]