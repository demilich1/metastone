package net.demilich.metastone.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class WeaponTests extends TestBase {
	
	@Test
	public void testWeapon() {
		DebugContext context = createContext(HeroClass.WARRIOR, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Hero warrior = player.getHero();

		WeaponCard weaponCard = (WeaponCard) CardCatalogue.getCardById("weapon_battle_axe");

		context.setActivePlayer(player.getId());
		context.getLogic().startTurn(player.getId());
		Assert.assertEquals(warrior.getAttack(), 0);
		context.getLogic().receiveCard(player.getId(), weaponCard);
		context.getLogic().performGameAction(player.getId(), weaponCard.play());
		Assert.assertEquals(warrior.getAttack(), 2);
		Assert.assertEquals(warrior.getWeapon().getDurability(), 2);

		attack(context, player, warrior, context.getPlayer2().getHero());
		Assert.assertEquals(warrior.getWeapon().getDurability(), 1);
	}
	
	@Test
	public void testKingsDefenderAttendee() {
		DebugContext context = createContext(HeroClass.WARRIOR, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Hero hero = player.getHero();
		
		playCard(context, player, CardCatalogue.getCardById("weapon_death_bite"));
		Assert.assertEquals(hero.getWeapon().getAttack(), 4);
		Assert.assertEquals(hero.getWeapon().getDurability(), 2);
		playCard(context, player, CardCatalogue.getCardById("minion_tournament_attendee"));
		Assert.assertEquals(player.getMinions().size(), 1);
		playCard(context, player, CardCatalogue.getCardById("weapon_kings_defender"));
		Assert.assertEquals(hero.getWeapon().getAttack(), 3);
		Assert.assertEquals(hero.getWeapon().getDurability(), 3);
		Assert.assertEquals(player.getMinions().size(), 0);
	}
	
	@Test
	public void testKingsDefenderHogger() {
		DebugContext context = createContext(HeroClass.WARRIOR, HeroClass.WARRIOR);
		Player player = context.getPlayer1();
		Hero hero = player.getHero();
		
		playCard(context, player, CardCatalogue.getCardById("weapon_death_bite"));
		Assert.assertEquals(hero.getWeapon().getAttack(), 4);
		Assert.assertEquals(hero.getWeapon().getDurability(), 2);
		playCard(context, player, CardCatalogue.getCardById("minion_hogger_doom_of_elwynn"));
		Assert.assertEquals(player.getMinions().size(), 1);
		playCard(context, player, CardCatalogue.getCardById("weapon_kings_defender"));
		Assert.assertEquals(hero.getWeapon().getAttack(), 3);
		Assert.assertEquals(hero.getWeapon().getDurability(), 2);
		Assert.assertEquals(player.getMinions().size(), 2);
	}

}
