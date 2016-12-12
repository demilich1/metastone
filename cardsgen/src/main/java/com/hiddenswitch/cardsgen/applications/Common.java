package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.SdkClientException;
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
import org.apache.hadoop.security.AccessControlException;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static com.amazonaws.util.EC2MetadataUtils.EC2_METADATA_ROOT;
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
		JavaPairRDD<TestConfig, SimulationResult> simulations = configs.repartition((int) configs.count()).mapValues(new Simulator());
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
		return "s3n://clusterresults/build/" + RandomStringUtils.randomAlphabetic(20);
	}

	public static AWSCredentials getAwsCredentials() {
		return getAwsCredentials("default");
	}

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
		if (isEC2Environment()) {
			return;
		}

		AWSCredentials credentials = getAwsCredentials();
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
}
