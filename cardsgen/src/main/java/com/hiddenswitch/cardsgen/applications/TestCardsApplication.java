package com.hiddenswitch.cardsgen.applications;

import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.MetaHero;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.statistics.SimulationResult;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestCardsApplication {
	public static void main(String[] args) throws IOException {
		Common.setLogLevelToError();

		SparkConf sc = new SparkConf().setMaster("local[8]").setAppName("Test cards");
		JavaSparkContext jc = new JavaSparkContext(sc);

		List<Tuple2<String, String>> deckPairs = jc
				.parallelize(Common.getDefaultDecks())
				.cartesian(jc.parallelize(Common.getDefaultDecks()))
				.collect();

		List<String> cards = Arrays.asList("spell_fireball", "minion_ironfur_grizzly", "minion_amgam_rager");

		JavaPairRDD<Tuple2<String, String>, String> deckPairsCards = jc.parallelize(deckPairs).cartesian(jc.parallelize(cards));

		JavaRDD<TestConfig> testConfigs = deckPairsCards.map((Tuple2<Tuple2<String, String>, String> deckPairCard) -> {
			TestConfig config = new TestConfig();
			Tuple2<String, String> deckPair = deckPairCard._1;
			String card = deckPairCard._2;
			config.setCardId(card);
			config.setDeckIdTest(deckPair._1);
			config.setDeckIdOpponent(deckPair._2);
			return config;
		});

		// Define simulations for each test config
		JavaPairRDD<TestConfig, GameConfig> gameConfigs = testConfigs.mapToPair((TestConfig testConfig) -> {
			CardCatalogue.loadCardsFromPackage();
			DeckCatalogue.loadDecksFromPackage();

			GameConfig gameConfig = new GameConfig();
			gameConfig.setNumberOfGames(8);
			PlayerConfig playerConfig1 = new PlayerConfig();
			playerConfig1.setName("Test player");
			Deck deck1 = DeckCatalogue.getDeckByName(testConfig.getDeckIdTest());
			// Add the cards
			if (testConfig.getCardId() != null) {
				Card card = CardCatalogue.getCardById(testConfig.getCardId());
				deck1.getCards().add(card);
				deck1.getCards().add(card);
			}
			playerConfig1.setDeck(deck1);
			playerConfig1.setHeroCard(MetaHero.getHeroCard(deck1.getHeroClass()));
			playerConfig1.setBehaviour(new GameStateValueBehaviour(FeatureVector.getFittest(), "[AI]"));
			playerConfig1.setHideCards(false);
			gameConfig.setPlayerConfig1(playerConfig1);
			PlayerConfig playerConfig2 = new PlayerConfig();
			playerConfig2.setName("Test player");
			Deck deck2 = DeckCatalogue.getDeckByName(testConfig.getDeckIdTest());
			playerConfig2.setDeck(deck2);
			playerConfig2.setHeroCard(MetaHero.getHeroCard(deck2.getHeroClass()));
			playerConfig2.setBehaviour(new GameStateValueBehaviour(FeatureVector.getFittest(), "[AI]"));
			playerConfig2.setHideCards(false);
			gameConfig.setPlayerConfig2(playerConfig2);

			return new Tuple2<TestConfig, GameConfig>(testConfig, gameConfig);
		});

		// Actually run the simulations
		JavaPairRDD<TestConfig, SimulationResult> simulations = gameConfigs.mapToPair((Tuple2<TestConfig, GameConfig> simulationConfig) -> {
			CardCatalogue.loadCardsFromPackage();
			TestConfig testConfig = simulationConfig._1();
			GameConfig gameConfig = simulationConfig._2();
			SimulationResult result = new SimulationResult(gameConfig);

			DeckFormat deckFormat = new DeckFormat();
			for (CardSet set : CardSet.values()) {
				deckFormat.addSet(set);
			}
			Player player1 = new Player(gameConfig.getPlayerConfig1());
			Player player2 = new Player(gameConfig.getPlayerConfig2());
			GameContext context = new GameContext(player1, player2, new GameLogic(), deckFormat);
			context.play();
			result.getPlayer1Stats().merge(context.getPlayer1().getStatistics());
			result.getPlayer2Stats().merge(context.getPlayer2().getStatistics());
			result.calculateMetaStatistics();
			context.dispose();
			return new Tuple2<>(testConfig, result);
		});

		JavaRDD<Tuple2<TestConfig, SimulationResult>> existingSimulations = jc.objectFile("build/results_game_02");
		JavaPairRDD<TestConfig, SimulationResult> existingSimulationsPair = JavaPairRDD.fromJavaRDD(existingSimulations);
		simulations = simulations
				.union(existingSimulationsPair)
				.reduceByKey((SimulationResult a, SimulationResult b) -> a.merge(b));

		simulations.saveAsObjectFile("build/results_game_03");
		simulations.saveAsTextFile("build/results_game_text_03");
	}
}