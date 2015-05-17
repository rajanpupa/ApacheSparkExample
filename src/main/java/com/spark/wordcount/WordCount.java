package com.spark.wordcount;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class WordCount {

	// Map each line to a list of words
	private static final FlatMapFunction<String, String> WORDS_EXTRACTOR = new FlatMapFunction<String, String>() {
		@Override
		public Iterable<String> call(String line) throws Exception {
			return Arrays.asList(line.split(" "));
		}
	};

	// Turn the words into (word, 1) pairs
	private static final PairFunction<String, String, Integer> WORDS_MAPPER = new PairFunction<String, String, Integer>() {
		@Override
		public Tuple2<String, Integer> call(String word) throws Exception {
			return new Tuple2<String, Integer>(word, 1);
		}
	};

	// Group up and add the pairs by key to produce counts
	private static final Function2<Integer, Integer, Integer> WORDS_REDUCER = new Function2<Integer, Integer, Integer>() {
		@Override
		public Integer call(Integer i1, Integer i2) throws Exception {
			return i1 + i2;
		}
	};

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err
					.println("Please provide the input file full path as argument");
			System.exit(0);
		}

		SparkConf conf = new SparkConf().setAppName("com.spark.WordCount")
				.setMaster("local");
		JavaSparkContext context = new JavaSparkContext(conf);

		JavaRDD<String> file = context.textFile(args[0]);
		JavaRDD<String> words = file.flatMap(WORDS_EXTRACTOR);
		JavaPairRDD<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
		JavaPairRDD<String, Integer> counter = pairs.reduceByKey(
				WORDS_REDUCER);

		counter.sortByKey();
		counter.saveAsTextFile(args[1]);
	}
}
