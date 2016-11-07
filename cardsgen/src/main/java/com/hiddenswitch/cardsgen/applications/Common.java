package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.hiddenswitch.cardsgen.functions.GenerateConfigsForDecks;
import com.hiddenswitch.cardsgen.functions.MergeSimulationResults;
import com.hiddenswitch.cardsgen.functions.Simulator;
import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.util.Combinations;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Common {
	public static JavaPairRDD<TestConfig, SimulationResult> simulate(JavaPairRDD<TestConfig, GameConfig> configs) {
		JavaPairRDD<TestConfig, SimulationResult> simulations = configs.repartition(8 * 16).mapValues(new Simulator());
		return simulations.reduceByKey(new MergeSimulationResults());
	}

	public static List<String[]> getDeckCombinations(List<String> decks) {
		// Create deck combinations
		Combinations combinations = new Combinations(decks.size(), 2);
		List<String[]> deckPairs = new ArrayList<>();
		for (int[] combination : combinations) {
			deckPairs.add(new String[]{decks.get(combination[0]), decks.get(combination[1])});
		}
		return deckPairs;
	}

	public static JavaPairRDD<TestConfig, GameConfig> getConfigsForDecks(JavaSparkContext sc, List<String> decks, int gamesPerBatch, int batches) throws IOException, URISyntaxException, CardParseException {
		List<String[]> deckPairs = getDeckCombinations(decks);


		JavaRDD<String[]> pairs = sc.parallelize(deckPairs);

		// Create game configs to simulate
		return pairs.flatMapToPair(new GenerateConfigsForDecks(batches, gamesPerBatch));
	}

	public static List<String> getDefaultDecks() throws IOException {
		return IOUtils.readLines(Common.class.getClassLoader().getResourceAsStream("defaults/decks.txt"));
	}

	public static String getTemporaryOutput() {
		return "build/" + RandomStringUtils.randomAlphabetic(20);
	}

	public static AWSCredentials getAwsCredentials() {
		return getAwsCredentials("default");
	}

	public static AWSCredentials getAwsCredentials(String profile) {
		return new AWSCredentialsProviderChain(new ProfileCredentialsProvider(profile),
				new EnvironmentVariableCredentialsProvider(),
				new SystemPropertiesCredentialsProvider(),
				new InstanceProfileCredentialsProvider()).getCredentials();
	}
}
