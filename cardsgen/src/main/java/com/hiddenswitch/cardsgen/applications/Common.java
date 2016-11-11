package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.util.EC2MetadataUtils;
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
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static com.amazonaws.util.EC2MetadataUtils.getIAMSecurityCredentials;

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
		// Include same deck matchups
		for (String deck : decks) {
			deckPairs.add(new String[]{deck, deck});
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
		return "s3n://clusterresults/build/" + RandomStringUtils.randomAlphabetic(20);
	}

	public static AWSCredentials getAwsCredentials() {
		return getAwsCredentials("default");
	}

	public static AWSCredentials getAwsCredentials(String profile) {
		try {
			Map<String, EC2MetadataUtils.IAMSecurityCredential> credentials = getIAMSecurityCredentials();
			// Get the first credentials here

			for (Map.Entry<String, EC2MetadataUtils.IAMSecurityCredential> entry : credentials.entrySet()) {
				return new BasicAWSCredentials(entry.getValue().accessKeyId, entry.getValue().secretAccessKey);
			}
		} catch (Exception ignored) {
		}

		if (profile == null || profile.isEmpty()) {
			return new AWSCredentialsProviderChain(
					new InstanceProfileCredentialsProvider(),
					new EnvironmentVariableCredentialsProvider(),
					new SystemPropertiesCredentialsProvider()
			).getCredentials();
		} else {
			return new AWSCredentialsProviderChain(
					new ProfileCredentialsProvider(profile),
					new EnvironmentVariableCredentialsProvider(),
					new SystemPropertiesCredentialsProvider()
			).getCredentials();
		}
	}

	public static void configureS3Credentials(JavaSparkContext sc, String sentinelKeyPrefix) {
		configureS3Credentials(sc, sentinelKeyPrefix, "default");
	}

	public static void configureS3Credentials(JavaSparkContext sc, String sentinelKeyPrefix, String profile) {
		Logger logger = Logger.getLogger(Common.class);
		try {
			// Try accessing saving a file on s3
			sc.parallelize(Collections.singletonList("sentinel")).saveAsObjectFile(sentinelKeyPrefix + RandomStringUtils.randomAlphanumeric(5) + ".txt");
		} catch (Exception e) {

			configureS3(sc, profile);
		}
	}

	public static void configureS3(JavaSparkContext sc, String profile) {
		Logger logger = Logger.getLogger(Common.class);
		AWSCredentials credentials = getAwsCredentials(profile);
		logger.info(String.format("The credentials retrieved from Common.getAwsCredentials are: %s %s", credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey()));
		Configuration configuration = sc.hadoopConfiguration();
		logger.info("Hadoop configuration before setting values:");
		for (Map.Entry<String, String> entry : configuration) {
			logger.info(String.format("%s: %s", entry.getKey(), entry.getValue()));
		}

		if (configuration.get("fs.s3n.impl", "").isEmpty()) {
			configuration.set("fs.s3n.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem");
		}

		configuration.set("fs.s3n.awsAccessKeyId", credentials.getAWSAccessKeyId());
		configuration.set("fs.s3n.awsSecretAccessKey", credentials.getAWSSecretKey());

		logger.info("Hadoop configuration after setting values:");
		for (Map.Entry<String, String> entry : configuration) {
			logger.info(String.format("%s: %s", entry.getKey(), entry.getValue()));
		}
	}

	static String defaultsTo(String msg, String value) {
		return String.format("%s Defaults to %s.", msg, value);
	}

	public static Logger getLogger(Class clazz) {
		ConsoleAppender console = new ConsoleAppender();
		console.setLayout(new SimpleLayout());
		console.setTarget("System.out");
		console.activateOptions();

		Logger.getRootLogger().addAppender(console);
		Logger.getRootLogger().setLevel(Level.INFO);
		return Logger.getLogger(clazz);
	}
}
