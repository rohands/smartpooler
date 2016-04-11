# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ActivePoolers',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('src', models.DecimalField(max_digits=10, decimal_places=5)),
                ('dest', models.DecimalField(max_digits=10, decimal_places=5)),
                ('start_datetime', models.DateTimeField()),
                ('end_datetime', models.DateTimeField()),
                ('car_type', models.IntegerField()),
                ('baggage', models.DecimalField(max_digits=10, decimal_places=2)),
                ('price', models.DecimalField(max_digits=10, decimal_places=1)),
            ],
        ),
        migrations.CreateModel(
            name='RegUsers',
            fields=[
                ('usn', models.CharField(max_length=10, serialize=False, primary_key=True)),
                ('rating', models.IntegerField()),
                ('phno', models.CharField(max_length=15)),
                ('emailid', models.EmailField(max_length=254)),
            ],
        ),
        migrations.AddField(
            model_name='activepoolers',
            name='usn',
            field=models.ForeignKey(to='smartpool.RegUsers'),
        ),
        migrations.AlterUniqueTogether(
            name='activepoolers',
            unique_together=set([('usn', 'start_datetime')]),
        ),
    ]
