package net.demilich.metastone.tests;


import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TheOldGodsTests extends TestBase {

	@Test
	public void testCallInTheFinishers() {
		GameContext context = createContext(HeroClass.SHAMAN, HeroClass.WARRIOR);
		Player player = context.getPlayer1();

		playCard(context, player, CardCatalogue.getCardById("spell_call_in_the_finishers"));

		for (Minion minion : player.getMinions()) {
			Assert.assertEquals(minion.getSourceCard().getCardId(), "token_murloc_razorgill");
		}
	}

	@Test
	public void testDarkshireCoucilman() {
		GameContext context = createContext(HeroClass.SHAMAN, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Player opponent = context.getOpponent(player);

		Minion darkshireCouncilman = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_darkshire_councilman"));
		Assert.assertEquals(darkshireCouncilman.getAttack(), darkshireCouncilman.getBaseAttack());

		Minion darkshireCouncilman2 = playMinionCard(context, player, (MinionCard) CardCatalogue.getCardById("minion_darkshire_councilman"));
		Assert.assertEquals(darkshireCouncilman.getAttack(), darkshireCouncilman.getBaseAttack() + 1);
		Assert.assertEquals(darkshireCouncilman2.getAttack(), darkshireCouncilman2.getBaseAttack());

		context.getLogic().endTurn(player.getId());
		Minion opponentMinion = playMinionCard(context, opponent, (MinionCard) CardCatalogue.getCardById("minion_darkshire_councilman"));

		Assert.assertEquals(darkshireCouncilman.getAttack(), darkshireCouncilman.getBaseAttack() + 1);
		Assert.assertEquals(darkshireCouncilman2.getAttack(), darkshireCouncilman2.getBaseAttack());
		Assert.assertEquals(opponentMinion.getAttack(), opponentMinion.getBaseAttack());
	}

}
