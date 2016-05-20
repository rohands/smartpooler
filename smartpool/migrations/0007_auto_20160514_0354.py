# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0006_activepoolers_filled'),
    ]

    operations = [
        migrations.AddField(
            model_name='activepoolers',
            name='waypoints_lat',
            field=models.CharField(default=b'', max_length=20),
        ),
        migrations.AddField(
            model_name='activepoolers',
            name='waypoints_lng',
            field=models.CharField(default=b'', max_length=20),
        ),
    ]
