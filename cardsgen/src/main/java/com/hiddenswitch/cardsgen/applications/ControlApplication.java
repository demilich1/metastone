package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.auth.AWSCredentials;
import com.hiddenswitch.cardsgen.functions.MergeSimulationResults;
import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Calculates control statistics for all the decks.
 */
public class ControlApplication {
	private static final String DECKS_FILE = "decksfile";
	private static final String GAMES_PER_BATCH = "gamesperbatch";
	private static final String BATCHES = "batches";
	private static final String OUTPUT = "output";
	private static final String INPUT = "input";
	private static final String PROFILE = "profile";

	public static void main(String[] args) throws ParseException, CardParseException, IOException, URISyntaxException {
		Logger logger = Logger.getLogger(ControlApplication.class);

		// Parse all the options
		Options options = new Options()
				.addOption(INPUT, true, "An existing input datastore.")
				.addOption(DECKS_FILE, true, "A path to a list of decks to evaluate")
				.addOption(GAMES_PER_BATCH, true, "The number of games per simulation batch to run. This is a " +
						"non-parallelized chunk of game simulations")
				.addOption(BATCHES, true, "The number of batches of games to simulate. This is a parallelizable " +
						"chunk of games to execute.")
				.addOption(OUTPUT, true, "The output file to save the training data to.")
				.addOption(PROFILE, true, "The AWS profile to use for s3n URLs.");

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

		String decksFile = null;
		int gamesPerBatch = 3;
		int batches = 2;
		String output = Common.getTemporaryOutput();
		String input = null;
		String profile = "default";

		if (cmd.hasOption(DECKS_FILE)) {
			decksFile = cmd.getOptionValue(DECKS_FILE);
		}

		if (cmd.hasOption(GAMES_PER_BATCH)) {
			gamesPerBatch = Integer.parseInt(cmd.getOptionValue(GAMES_PER_BATCH));
		}

		if (cmd.hasOption(BATCHES)) {
			batches = Integer.parseInt(cmd.getOptionValue(BATCHES));
		}

		if (cmd.hasOption(OUTPUT)) {
			output = cmd.getOptionValue(OUTPUT);
		}

		if (cmd.hasOption(INPUT)) {
			input = cmd.getOptionValue(INPUT);
		}

		if (cmd.hasOption(PROFILE)) {
			profile = cmd.getOptionValue(PROFILE);
		}

		// Start Spark
		SparkConf conf = new SparkConf().setAppName("Compute control statistics");
		JavaSparkContext sc = new JavaSparkContext(conf);
		AWSCredentials credentials = Common.getAwsCredentials(profile);
		Configuration configuration = sc.hadoopConfiguration();
		configuration.set("fs.s3n.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem");
		configuration.set("fs.s3n.awsAccessKeyId", credentials.getAWSAccessKeyId());
		configuration.set("fs.s3n.awsSecretAccessKey", credentials.getAWSSecretKey());

		// Load the decks
		List<String> decks = null;

		if (decksFile != null) {
			decks = sc.textFile(decksFile).collect();
		} else {
			decks = Common.getDefaultDecks();

		}

		JavaPairRDD<TestConfig, GameConfig> configs = Common.getConfigsForDecks(sc, decks, gamesPerBatch, batches);

		// Simulate
		JavaPairRDD<TestConfig, SimulationResult> results = Common.simulate(configs);

		// Get existing results to merge them
		if (input != null) {
			JavaPairRDD<TestConfig, SimulationResult> existing = null;
			try {
				existing = JavaPairRDD.fromJavaRDD(sc.objectFile(input));
			} catch (Exception e) {
				logger.error(String.format("Could not load existing result from path %s", input), e);
			}

			try {
				if (existing != null
						|| existing.count() == 0) {
					results = results.union(existing).reduceByKey(new MergeSimulationResults());
				} else {
					logger.info("Existing results to merge were not loaded correctly or there were zero records.");
				}
			} catch (Exception e) {
				logger.error(String.format("Could not merge simulation results from path %s", input));
			}
		}

		// Save results
		results.saveAsObjectFile(output);
		results.saveAsTextFile(output + "_text");
	}


}
