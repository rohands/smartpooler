# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0002_auto_20160421_1136'),
    ]

    operations = [
        migrations.AddField(
            model_name='activepoolers',
            name='waypoints',
            field=models.CharField(default='', max_length=10000),
            preserve_default=False,
        ),
    ]
