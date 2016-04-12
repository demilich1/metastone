package net.demilich.metastone.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class CloningTest extends TestBase {

	private static Logger logger = LoggerFactory.getLogger(CloningTest.class);

	private void compareCardCollections(CardCollection collection1, CardCollection collection2) {
		Assert.assertEquals(collection1.getCount(), collection2.getCount());
		Assert.assertNotSame(collection1, collection2);
		for (int j = 0; j < collection1.getCount(); j++) {
			Card originalCard = collection1.get(j);
			logger.debug("Original card: " + originalCard);
			Card cloneCard = collection2.get(j);
			logger.debug("Clone card: " + cloneCard);
			Assert.assertNotSame(originalCard, cloneCard);
			if (originalCard instanceof SpellCard) {
				Assert.assertTrue(cloneCard instanceof SpellCard, "cloneCard is instanceof " + cloneCard.getClass().getSimpleName());
				SpellCard originalSpellCard = (SpellCard) originalCard;
				SpellCard cloneSpellCard = (SpellCard) cloneCard;
				Assert.assertNotSame(originalSpellCard.getSpell(), cloneSpellCard.getSpell());
			}
		}
	}

	@Test
	public void testCloneSpellCard() {
		Card original = CardCatalogue.getCardById("spell_polymorph");
		Card clone = original.clone();
		Assert.assertNotSame(original, clone);
		SpellCard originalSpellCard = (SpellCard) original;
		SpellCard cloneSpellCard = (SpellCard) clone;
		Assert.assertNotSame(originalSpellCard.getSpell(), cloneSpellCard.getSpell());
	}

	@Test
	public void testCloning() {
		for (int i = 0; i < 100; i++) {
			PlayerConfig player1Config = new PlayerConfig(DeckFactory.getRandomDeck(HeroClass.MAGE), new PlayRandomBehaviour());
			player1Config.setName("Player 1");
			player1Config.setHeroCard(getHeroCardForClass(HeroClass.MAGE));
			Player player1 = new Player(player1Config);

			PlayerConfig player2Config = new PlayerConfig(DeckFactory.getRandomDeck(HeroClass.WARRIOR), new PlayRandomBehaviour());
			player2Config.setName("Player 2");
			player2Config.setHeroCard(getHeroCardForClass(HeroClass.WARRIOR));
			Player player2 = new Player(player2Config);

			DeckFormat deckFormat = new DeckFormat();
			for (CardSet set : CardSet.values()) {
				deckFormat.addSet(set);
			}

			GameContext original = new GameContext(player1, player2, new GameLogic(), deckFormat);
			TestMinionCard minionCard = new TestMinionCard(3, 3);
			original.getLogic().receiveCard(player1.getId(), minionCard);
			original.getLogic().performGameAction(player1.getId(), minionCard.play());
			Actor testMinion = minionCard.getMinion();

			GameContext clone = original.clone();

			Assert.assertNotSame(original, clone);
			Assert.assertNotSame(original.getPlayer1(), clone.getPlayer1());

			logger.debug("Comparing hands");
			compareCardCollections(original.getPlayer1().getHand(), clone.getPlayer1().getHand());
			logger.debug("Comparing decks");
			compareCardCollections(original.getPlayer1().getDeck(), clone.getPlayer1().getDeck());

			GameContext cloneOfClone = clone.clone();
			compareCardCollections(clone.getPlayer1().getHand(), cloneOfClone.getPlayer1().getHand());
			compareCardCollections(clone.getPlayer1().getDeck(), cloneOfClone.getPlayer1().getDeck());

			Assert.assertNotSame(original.getPlayer2().getMinions(), clone.getPlayer2().getMinions());
			Actor originalMinion = getSingleMinion(original.getPlayer1().getMinions());
			Actor cloneMinion = getSingleMinion(clone.getPlayer1().getMinions());
			Assert.assertNotSame(originalMinion, cloneMinion);
			Assert.assertEquals(original.getPlayer2().getMinions().size(), clone.getPlayer2().getMinions().size());
			Assert.assertEquals(original.getPlayer1().getMana(), clone.getPlayer1().getMana());

			clone.play();
			logger.info("");
			logger.info("********ORIGINAL********");
			logger.info(original.toString());
			logger.info("");
			logger.info("********CLONE********");
			logger.info(clone.toString());
			Assert.assertNotEquals(original.getTurn(), clone.getTurn());
			Assert.assertEquals(testMinion.getHp(), 3);
		}
	}
}
