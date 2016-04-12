package net.demilich.metastone.tests;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class MassTest extends TestBase {

	private static HeroClass getRandomClass() {
		HeroClass randomClass = HeroClass.ANY;
		HeroClass[] values = HeroClass.values();
		while (randomClass == HeroClass.ANY || randomClass == HeroClass.DECK_COLLECTION || randomClass == HeroClass.OPPONENT || randomClass == HeroClass.BOSS) {
			randomClass = values[ThreadLocalRandom.current().nextInt(values.length)];
		}
		return randomClass;
	}

	@BeforeTest
	private void loggerSetup() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
	}

	@Test(threadPoolSize = 16, invocationCount = 1000)
	public void testRandomMassPlay() {
		HeroClass heroClass1 = getRandomClass();
		PlayerConfig player1Config = new PlayerConfig(DeckFactory.getRandomDeck(heroClass1), new PlayRandomBehaviour());
		player1Config.setName("Player 1");
		player1Config.setHeroCard(getHeroCardForClass(heroClass1));
		Player player1 = new Player(player1Config);

		HeroClass heroClass2 = getRandomClass();
		PlayerConfig player2Config = new PlayerConfig(DeckFactory.getRandomDeck(heroClass2), new PlayRandomBehaviour());
		player2Config.setName("Player 2");
		player2Config.setHeroCard(getHeroCardForClass(heroClass2));
		Player player2 = new Player(player2Config);
		DeckFormat deckFormat = new DeckFormat();
		for (CardSet set : CardSet.values()) {
			deckFormat.addSet(set);
		}
		GameContext context = new GameContext(player1, player2, new GameLogic(), deckFormat);
		try {
			context.play();
			context.dispose();
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}

	}

}
