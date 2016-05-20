# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0007_auto_20160514_0354'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='activepoolers',
            name='waypoints_lat',
        ),
        migrations.RemoveField(
            model_name='activepoolers',
            name='waypoints_lng',
        ),
        migrations.AddField(
            model_name='activepoolers',
            name='waypoints_usn',
            field=models.CharField(default=b'', max_length=10000),
        ),
    ]
