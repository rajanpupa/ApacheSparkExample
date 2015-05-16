#!/bin/bash

hadoop fs -rm -r ipsumoutput
rm -f output/*
../spark-1.3.1-bin-hadoop2.6/bin/spark-submit --class com.spark.WordCount --master local[2] ./target/ApacheSparkExample-0.0.1-SNAPSHOT.jar ipsuminput/*.txt ipsumoutput

hadoop fs -get ipsumoutput/* output
