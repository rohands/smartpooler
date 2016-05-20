# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0004_remove_activepoolers_end_datetime'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='activepoolers',
            name='waypoints',
        ),
    ]
