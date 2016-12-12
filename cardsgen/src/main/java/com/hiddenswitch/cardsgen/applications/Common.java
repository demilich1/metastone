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
import org.apache.commons.math3.util.Combinations;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazonaws.util.EC2MetadataUtils.getIAMSecurityCredentials;

public class Common {
	private static Optional<Boolean> isEC2Environment = Optional.empty();

	public static boolean isEC2Environment() {
		if (isEC2Environment.isPresent()) {
			return isEC2Environment.get();
		} else {
			final boolean result = getEC2Environment();
			isEC2Environment = Optional.of(result);
			return result;
		}

	}

	private static boolean getEC2Environment() {
		File hypervisor = new File("/sys/hypervisor/uuid");

		if (!hypervisor.exists()) {
			return false;
		}

		try {
			FileInputStream reader = new FileInputStream(hypervisor);
			byte[] bytes = new byte[3];
			int read = reader.read(bytes);
			reader.close();

			if (read != 3) {
				return false;
			}

			// Do these three bytes equal ec2?
			return bytes[0] == 101
					&& bytes[1] == 99
					&& bytes[2] == 50;
		} catch (IOException e) {
			return false;
		}
	}

	public static JavaPairRDD<TestConfig, SimulationResult> simulate(JavaPairRDD<TestConfig, GameConfig> configs) {
		final StorageLevel storageLevel = new StorageLevel(true, true, false, false, 10);
		JavaPairRDD<TestConfig, SimulationResult> simulations = configs.repartition((int) configs.count()).persist(storageLevel).mapValues(new Simulator()).persist(storageLevel);
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

	public static List<String[]> getDeckCombinations(List<String> decks, boolean complete) {
		if (complete) {
			List<String[]> deckPairs = new ArrayList<>();
			for (String deck1 : decks) {
				for (String deck2 : decks) {
					deckPairs.add(new String[]{deck1, deck2});
				}
			}
			return deckPairs;
		} else {
			return getDeckCombinations(decks);
		}
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

	public static String getDatedOutput() {
		return "s3n://clusterresults/build/" + getTimestampString();
	}

	private static String getTimestampString() {
		return new SimpleDateFormat("yyyyMMDD hhmmss").format(Date.from(Instant.now())) + " " + Long.toString(System.nanoTime());
	}

	public static AWSCredentials getAwsCredentials() {
		return getAwsCredentials("default");
	}

	@SuppressWarnings("deprecation")
	public static AWSCredentials getAwsCredentials(String profile) {
		if (isEC2Environment()) {
			try {
				Map<String, EC2MetadataUtils.IAMSecurityCredential> credentials = getIAMSecurityCredentials();
				// Get the first credentials here

				for (Map.Entry<String, EC2MetadataUtils.IAMSecurityCredential> entry : credentials.entrySet()) {
					return new BasicAWSCredentials(entry.getValue().accessKeyId, entry.getValue().secretAccessKey);
				}
			} catch (Exception ignored) {
			}
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

	public static void configureS3Credentials(JavaSparkContext sc) {
		configureS3Credentials(sc, "default");
	}

	public static void configureS3Credentials(JavaSparkContext sc, String profile) {
		if (isEC2Environment()) {
			return;
		}

		AWSCredentials credentials = getAwsCredentials(profile);
		Configuration configuration = sc.hadoopConfiguration();

		if (configuration.get("fs.s3n.impl", "").isEmpty()) {
			configuration.set("fs.s3n.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem");
		}
		if (configuration.get("fs.s3n.awsAccessKeyId", "").isEmpty()) {
			configuration.set("fs.s3n.awsAccessKeyId", credentials.getAWSAccessKeyId());
			configuration.set("fs.s3n.awsSecretAccessKey", credentials.getAWSSecretKey());
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

	public static void setLogLevelToError() {
		Object rootObject = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		if (rootObject instanceof ch.qos.logback.classic.Logger) {
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) rootObject;
			root.setLevel(ch.qos.logback.classic.Level.ERROR);
		} else if (rootObject instanceof Logger) {
			Logger root = (Logger) rootObject;
			root.setLevel(Level.ERROR);
		}
	}
}
