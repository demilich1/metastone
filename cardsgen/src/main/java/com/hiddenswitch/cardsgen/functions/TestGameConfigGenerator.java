package com.hiddenswitch.cardsgen.functions;

import com.hiddenswitch.cardsgen.models.TestConfig;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.MetaHero;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

public class TestGameConfigGenerator implements PairFlatMapFunction<Card, TestConfig, GameConfig> {
	private final int batches;
	private final int gamesPerBatch;

	public TestGameConfigGenerator(int batches, int gamesPerBatch) throws IOException, URISyntaxException, CardParseException {
		this.batches = batches;
		this.gamesPerBatch = gamesPerBatch;
		CardCatalogue.loadCards();
		DeckCatalogue.loadDecks();
	}

	@Override
	public Iterator<Tuple2<TestConfig, GameConfig>> call(Card card) throws Exception {
		// TODO: For now, return one game config for each card.
		Deck testDeck = DeckCatalogue.getDeckByName("Elise Mage");
		Deck opponentDeck = DeckCatalogue.getDeckByName("Midrange Hunter");
		assert testDeck != null;
		assert opponentDeck != null;

		// Add two copies of the card under test to the test deck
		testDeck.getCards().add(card);
		testDeck.getCards().add(card);

		PlayerConfig testPlayerConfig = new PlayerConfig(testDeck, new GameStateValueBehaviour(FeatureVector.getFittest(), "testPlayer "));
		testPlayerConfig.setHeroCard(MetaHero.getHeroCard(testDeck.getHeroClass()));
		PlayerConfig opponentPlayerConfig = new PlayerConfig(opponentDeck, new GameStateValueBehaviour(FeatureVector.getFittest(), "testPlayer "));
		opponentPlayerConfig.setHeroCard(MetaHero.getHeroCard(opponentDeck.getHeroClass()));
		GameConfig gameConfig = new GameConfig();
		gameConfig.setPlayerConfig1(testPlayerConfig);
		gameConfig.setPlayerConfig2(opponentPlayerConfig);
		DeckFormat deckFormat = new DeckFormat();
		deckFormat.addSet(CardSet.ANY);
		gameConfig.setDeckFormat(deckFormat);
		// Process games in batches of ten
		gameConfig.setNumberOfGames(gamesPerBatch);

		TestConfig testConfig = new TestConfig();
		testConfig.setCardId(card.getCardId());
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
