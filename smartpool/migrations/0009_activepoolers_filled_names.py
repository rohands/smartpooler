# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('smartpool', '0008_auto_20160514_0404'),
    ]

    operations = [
        migrations.AddField(
            model_name='activepoolers',
            name='filled_names',
            field=models.CharField(default=b'', max_length=10000000),
        ),
    ]
