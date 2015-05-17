package com.spark.crimecategorize;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class CrimeCategorize {
	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("com.spark.WordCount")
				.setMaster("local");
		
		JavaSparkContext context = new JavaSparkContext(conf);

		JavaRDD<String> lines = context.textFile("src/main/resources/crime*.txt");
		
		JavaRDD<String> words = lines.flatMap( line -> Arrays.asList(line.split(",")[2]) );
		
		JavaPairRDD<String, Integer> pairs = words.mapToPair( word->new Tuple2<String, Integer>(word,1) );
		
		JavaPairRDD<String, Integer> counter = pairs.reduceByKey( (i1,i2)->(i1+i2) );

		counter.sortByKey();
		counter.saveAsTextFile("crimeOutput");
	}
}
