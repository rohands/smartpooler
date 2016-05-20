# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0005_remove_activepoolers_waypoints'),
    ]

    operations = [
        migrations.AddField(
            model_name='activepoolers',
            name='filled',
            field=models.IntegerField(default=0),
        ),
    ]
