package com.hiddenswitch.cardsgen.applications;

import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
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
	public static final String DECKS_FILE = "decksfile";
	public static final String GAMES_PER_BATCH = "gamesperbatch";
	public static final String BATCHES = "batches";
	public static final String OUTPUT = "output";

	public static void main(String[] args) throws ParseException, CardParseException, IOException, URISyntaxException {
		// Parse all the options

		Options options = new Options()
				.addOption(DECKS_FILE, true, "A path to a list of decks to evaluate")
				.addOption(GAMES_PER_BATCH, true, "The number of games per simulation batch to run. This is a " +
						"non-parallelized chunk of game simulations")
				.addOption(BATCHES, true, "The number of batches of games to simulate. This is a parallelizable " +
						"chunk of games to execute.")
				.addOption(OUTPUT, true, "The output file to save the training data to.");

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

		String decksFile = null;
		int gamesPerBatch = 3;
		int batches = 2;
		String output = Common.getTemporaryOutput();

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

		// Start Spark
		SparkConf conf = new SparkConf().setMaster("local[8]").setAppName("Compute control statistics");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// Load the decks
		List<String> decks = null;

		if (decksFile != null) {
			decks = IOUtils.readLines(new FileInputStream(new File(decksFile)));
		} else {
			decks = Common.getDefaultDecks();

		}

		JavaPairRDD<TestConfig, GameConfig> configs = Common.getConfigsForDecks(sc, decks, gamesPerBatch, batches);

		// Simulate
		JavaPairRDD<TestConfig, SimulationResult> results = Common.simulate(configs);

		// Save results
		results.saveAsObjectFile(output);
		results.saveAsTextFile(output + "_text");
	}


}
