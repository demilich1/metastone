package com.hiddenswitch.cardsgen.applications;

import com.hiddenswitch.cardsgen.functions.CardGenerator;
import com.hiddenswitch.cardsgen.functions.MergeSimulationResults;
import com.hiddenswitch.cardsgen.functions.Simulator;
import com.hiddenswitch.cardsgen.functions.TestGameConfigGenerator;
import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class SparkApplication {
	public static void main(String[] args) throws CardParseException, IOException, URISyntaxException {
		SparkConf conf = new SparkConf().setMaster("local[8]").setAppName("Test");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("ERROR");
		// TODO: Read the templates to compute from a file or argument.
		JavaRDD<String> templates = sc.parallelize(Arrays.asList("Deal N Damage"));
		JavaRDD<Card> cards = templates.flatMap(new CardGenerator());
		JavaPairRDD<TestConfig, GameConfig> configs = cards.flatMapToPair(new TestGameConfigGenerator(10, 10));
		JavaPairRDD<TestConfig, SimulationResult> simulations = configs.repartition(8 * 16).mapValues(new Simulator());
		JavaPairRDD<TestConfig, SimulationResult> results = simulations.reduceByKey(new MergeSimulationResults());
		results.saveAsTextFile("build/" + RandomStringUtils.randomAlphanumeric(20));
	}
}
