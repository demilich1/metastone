package net.demilich.metastone.tests;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.gui.gameconfig.PlayerConfig;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class MassTest extends TestBase {
	
	@BeforeTest
	private void loggerSetup() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
	}

	@Test(threadPoolSize = 16, invocationCount = 10000)
	public void testRandomMassPlay() {
		PlayerConfig player1Config = new PlayerConfig(DeckFactory.getRandomDeck(getRandomClass()), new PlayRandomBehaviour());
		player1Config.setName("Player 1");
		Player player1 = new Player(player1Config);

		PlayerConfig player2Config = new PlayerConfig(DeckFactory.getRandomDeck(getRandomClass()), new PlayRandomBehaviour());
		player2Config.setName("Player 2");
		Player player2 = new Player(player2Config);
		GameContext context = new GameContext(player1, player2, new GameLogic());
		try {
			context.play();
			context.dispose();
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}

	}

	private static HeroClass getRandomClass() {
		HeroClass randomClass = HeroClass.ANY;
		HeroClass[] values = HeroClass.values();
		while (randomClass == HeroClass.ANY || randomClass == HeroClass.DECK_COLLECTION) {
			randomClass = values[ThreadLocalRandom.current().nextInt(values.length)];
		}
		return randomClass;
	}

}
