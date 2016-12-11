package com.hiddenswitch.cardsgen.applications;

import ch.qos.logback.classic.Level;
import com.hiddenswitch.cardsgen.functions.MergeSimulationResults;
import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.hiddenswitch.cardsgen.applications.Common.defaultsTo;

/**
 * Calculates control statistics for all the decks.
 */
public class ControlApplication {
	private static final String DECKS_FILE = "decksfile";
	private static final String GAMES_PER_BATCH = "gamesperbatch";
	private static final String BATCHES = "batches";
	private static final String OUTPUT = "output";
	private static final String INPUT = "input";
	private static final String SENTINEL_KEY_PREFIX = "sentinelkeyprefix";

	public static void main(String[] args) throws ParseException, CardParseException, IOException, URISyntaxException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		Logger logger = Logger.getLogger(ControlApplication.class);

		String decksFile = null;
		int gamesPerBatch = 1;
		int batches = 1;
		String output = Common.getTemporaryOutput();
		String input = null;
		String sentinelKeyPrefix = "s3n://clusterresults/sentinel/";

		// Parse all the options
		Options options = new Options()
				.addOption(INPUT, true, "An existing input datastore.")
				.addOption(DECKS_FILE, true, defaultsTo("A path to a list of decks to evaluate.", "the 46 decks in the package"))
				.addOption(GAMES_PER_BATCH, true, defaultsTo("The number of games per simulation batch to run. This is a " +
						"non-parallelized chunk of game simulations", Integer.toString(gamesPerBatch)))
				.addOption(BATCHES, true, defaultsTo("The number of batches of games to simulate. This is a parallelizable " +
						"chunk of games to execute.", Integer.toString(batches)))
				.addOption(OUTPUT, true, defaultsTo("The output file to save the training data to.", output))
				.addOption(SENTINEL_KEY_PREFIX, true, defaultsTo("A path to try writing to to verify that we have AWS permissions (when applicable).", sentinelKeyPrefix));

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

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

		if (cmd.hasOption(SENTINEL_KEY_PREFIX)) {
			sentinelKeyPrefix = cmd.getOptionValue(SENTINEL_KEY_PREFIX);
		}

		// Start Spark
		SparkConf conf = new SparkConf().setAppName("Compute control statistics").setMaster("local[8]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		if ((input != null && (input.contains("s3n://") || input.contains("s3://") || input.contains("s3a://")))
				|| output.contains("s3n://") || output.contains("s3://") || output.contains("s3a://")) {
			Common.configureS3Credentials(sc, sentinelKeyPrefix);
		}


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

		sc.stop();
	}
}
