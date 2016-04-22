# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='activepoolers',
            name='destination',
            field=models.CharField(default='Null', max_length=20),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='activepoolers',
            name='source',
            field=models.CharField(default='', max_length=20),
            preserve_default=False,
        ),
    ]
