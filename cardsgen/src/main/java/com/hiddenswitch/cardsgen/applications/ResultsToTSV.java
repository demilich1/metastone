package com.hiddenswitch.cardsgen.applications;

import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import net.demilich.metastone.game.statistics.Statistic;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class ResultsToTSV {
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	public static final String PROFILE = "profile";

	public static void main(String args[]) throws ParseException {
		Logger logger = Common.getLogger(ResultsToTSV.class);

		String input = null;
		String output = Common.getTemporaryOutput() + "/output.tsv";
		String profile = "default";

		Options options = new Options()
				.addOption(INPUT, true, Common.defaultsTo("The input S3 results to parse.", "s3://clusterresults/output"))
				.addOption(OUTPUT, true, Common.defaultsTo("The output file to write a TSV to.", "build/{random}/output.tsv"))
				.addOption(PROFILE, true, Common.defaultsTo("The AWS profile to login with.", "default"));

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

		if (options.hasOption(INPUT)) {
			input = cmd.getOptionValue(INPUT);
		} else {
			throw new java.lang.IllegalArgumentException("An input argument is required.");
		}

		if (options.hasOption(OUTPUT)) {
			output = cmd.getOptionValue(OUTPUT);
		}

		if (options.hasOption(PROFILE)) {
			profile = cmd.getOptionValue(PROFILE);
		}

		SparkConf conf = new SparkConf().setMaster("local[1]").setAppName("S3 results to TSV");
		JavaSparkContext sc = new JavaSparkContext(conf);

		Configuration hadoopConfig = sc.hadoopConfiguration();
		hadoopConfig.set("fs.hdfs.impl",
				org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
		);
		hadoopConfig.set("fs.file.impl",
				org.apache.hadoop.fs.LocalFileSystem.class.getName()
		);

		Common.configureS3Credentials(sc, profile);

		JavaPairRDD<TestConfig, SimulationResult> results = JavaPairRDD.fromJavaRDD(sc.objectFile(input));

		logger.info(String.format("Number of records loaded: %d", results.count()));
		results.map((Tuple2<TestConfig, SimulationResult> record) -> {
			String id = record._1.getCardId();
			if (id == null) {
				id = "CONTROL_DECK";
			}
			Object player1Stats = record._2.getPlayer1Stats().get(Statistic.GAMES_WON);
			if (player1Stats == null) {
				player1Stats = 0;
			}
			return Arrays.asList(id,
					record._1.getDeckIdTest(),
					record._1.getDeckIdOpponent(),
					player1Stats.toString(),
					Integer.toString(record._2.getNumberOfGames()));
		}).map((List<String> row) -> String.join("\t", row))
				.coalesce(1)
				.saveAsTextFile(output);
	}
}
