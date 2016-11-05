package com.hiddenswitch.cardsgen.functions;

import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.MetaHero;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

public class GenerateConfigsForDecks implements PairFlatMapFunction<String[], TestConfig, GameConfig> {
	private final int batches;
	private final int gamesPerBatch;

	public GenerateConfigsForDecks(int batches, int gamesPerBatch) throws IOException, URISyntaxException, CardParseException {
		this.batches = batches;
		this.gamesPerBatch = gamesPerBatch;
		CardCatalogue.loadCards();
		DeckCatalogue.loadDecks();
	}

	@Override
	public Iterator<Tuple2<TestConfig, GameConfig>> call(String[] decks) throws Exception {
		Deck testDeck = DeckCatalogue.getDeckByName(decks[0]);
		Deck opponentDeck = DeckCatalogue.getDeckByName(decks[1]);

		PlayerConfig testPlayerConfig = new PlayerConfig(testDeck, new GameStateValueBehaviour(FeatureVector.getFittest(), "testPlayer "));
		testPlayerConfig.setName("[Test Player]");
		testPlayerConfig.setHeroCard(MetaHero.getHeroCard(testDeck.getHeroClass()));

		PlayerConfig opponentPlayerConfig = new PlayerConfig(opponentDeck, new GameStateValueBehaviour(FeatureVector.getFittest(), "opponentPlayer "));
		opponentPlayerConfig.setName("[Opponent Player]");
		opponentPlayerConfig.setHeroCard(MetaHero.getHeroCard(opponentDeck.getHeroClass()));
		GameConfig gameConfig = new GameConfig();
		gameConfig.setPlayerConfig1(testPlayerConfig);
		gameConfig.setPlayerConfig2(opponentPlayerConfig);
		DeckFormat deckFormat = new DeckFormat();

		for (CardSet set : CardSet.values()) {
			deckFormat.addSet(set);
		}

		gameConfig.setDeckFormat(deckFormat);
		// Process games in batches of ten
		gameConfig.setNumberOfGames(gamesPerBatch);

		TestConfig testConfig = new TestConfig();
		testConfig.setDeckIdTest(testDeck.getName());
		testConfig.setDeckIdOpponent(opponentDeck.getName());

		// Create gameCount games to test
		ArrayList<Tuple2<TestConfig, GameConfig>> testConfigs = new ArrayList<>();
		for (int i = 0; i < batches; i++) {
			testConfigs.add(i, new Tuple2<>(testConfig, gameConfig.clone()));
		}

		return testConfigs.iterator();
	}
}
