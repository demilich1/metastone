package com.hiddenswitch.proto3.net.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.reflect.TypeToken;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.*;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.cards.*;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.tests.TestBase;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.*;

public class SerializationTest extends TestBase {

	private static HeroClass getRandomClass() {
		HeroClass randomClass = HeroClass.ANY;
		HeroClass[] values = HeroClass.values();
		while (!randomClass.isBaseClass()) {
			randomClass = values[ThreadLocalRandom.current().nextInt(values.length)];
		}
		return randomClass;
	}

	@BeforeTest
	private void loggerSetup() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
	}

	@Test(threadPoolSize = 16, invocationCount = 1)
	public void testGameContextSerialization() {
		DeckFormat deckFormat = new DeckFormat();
		for (CardSet set : CardSet.values()) {
			deckFormat.addSet(set);
		}
		HeroClass heroClass1 = getRandomClass();
		PlayerConfig player1Config = new PlayerConfig(DeckFactory.getRandomDeck(heroClass1, deckFormat), new PlayRandomBehaviour());
		player1Config.setName("Player 1");
		player1Config.setHeroCard(getHeroCardForClass(heroClass1));
		Player player1 = new Player(player1Config);

		HeroClass heroClass2 = getRandomClass();
		PlayerConfig player2Config = new PlayerConfig(DeckFactory.getRandomDeck(heroClass2, deckFormat), new PlayRandomBehaviour());
		player2Config.setName("Player 2");
		player2Config.setHeroCard(getHeroCardForClass(heroClass2));
		Player player2 = new Player(player2Config);
		GameContext context = new SerializationTestContext(player1, player2, new GameLogic(), deckFormat);
		try {
			context.play();
			context.dispose();
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}
	}

	@Test
	public void testTypedSerialization() {
		PhysicalAttackAction action = new PhysicalAttackAction(EntityReference.ALL_MINIONS);
		String s = Serialization.serialize(action);
		PhysicalAttackAction deserializedAction = Serialization.deserialize(s, PhysicalAttackAction.class);
		assertEquals(deserializedAction.getAttackerReference(), action.getAttackerReference());

		GameAction deserializedGameAction = Serialization.deserialize(s, GameAction.class);
		PhysicalAttackAction castedAction = (PhysicalAttackAction) deserializedGameAction;
		assertEquals(castedAction.getAttackerReference(), action.getAttackerReference());
	}

	@Test
	public void testAllGameActions() {
		SpellCard fireball = (SpellCard) CardCatalogue.getCardById("spell_fireball");
		MinionCard elven_archer = (MinionCard) CardCatalogue.getCardById("minion_elven_archer");
		HeroPower heroPowerFireblast = (HeroPower) CardCatalogue.getCardById("hero_power_fireblast");
		WeaponCard assassinsBlade = (WeaponCard) CardCatalogue.getCardById("weapon_assassins_blade");
		SpellCard journeyBelow = (SpellCard) CardCatalogue.getCardById("spell_journey_below");
		CardCollection discoverCards = new CardCollection();
		discoverCards.add(fireball.getCopy());
		List<GameAction> discoverActions = SpellUtils.getDiscoverActionsForDiscoverSpell(journeyBelow.getSpell(), discoverCards);
		DiscoverAction discoverAction = (DiscoverAction) discoverActions.get(0);

		ArrayList<GameAction> gameActions = new ArrayList<>();

		EndTurnAction endTurnAction = new EndTurnAction();
		PhysicalAttackAction physicalAttackAction = new PhysicalAttackAction(new EntityReference(1));
		physicalAttackAction.setTargetKey(new EntityReference(2));
		PlaySpellCardAction playSpellCardAction = new PlaySpellCardAction(fireball.getSpell(), fireball, TargetSelection.ENEMY_CHARACTERS);
		playSpellCardAction.setTargetKey(new EntityReference(3));
		BattlecryAction elvenArcherAction = elven_archer.summon().getBattlecry();
		PlayMinionCardAction playMinionCardAction = new PlayMinionCardAction(elven_archer.getCardReference(), elvenArcherAction);
		HeroPowerAction heroPowerAction = new HeroPowerAction(heroPowerFireblast.getSpell(), heroPowerFireblast.getCopy(), TargetSelection.ENEMY_CHARACTERS);
		PlayWeaponCardAction playWeaponCardAction = new PlayWeaponCardAction(assassinsBlade.getCardReference());

		// 0
		gameActions.add(endTurnAction);
		// 1
		gameActions.add(physicalAttackAction);
		// 2
		gameActions.add(playSpellCardAction);
		// 3
		gameActions.add(playMinionCardAction);
		// 4
		gameActions.add(elvenArcherAction);
		// 5
		gameActions.add(heroPowerAction);
		// 6
		gameActions.add(playWeaponCardAction);
		// 7
		gameActions.add(discoverAction);

		String s = Serialization.serialize(gameActions);
		TypeToken<ArrayList<GameAction>> gameActionListTT = new TypeToken<ArrayList<GameAction>>() {
		};
		ArrayList<GameAction> deserializedGameActions = Serialization.deserialize(s, gameActionListTT.getType());

		EndTurnAction endTurnAction1 = (EndTurnAction) deserializedGameActions.get(0);
		assertNotNull(endTurnAction1);
		assertEquals(endTurnAction1.getActionType(), ActionType.END_TURN);

		PhysicalAttackAction physicalAttackAction1 = (PhysicalAttackAction) deserializedGameActions.get(1);
		assertNotNull(physicalAttackAction1);
		assertEquals(physicalAttackAction1.getActionType(), ActionType.PHYSICAL_ATTACK);
		assertEquals(physicalAttackAction1.getAttackerReference(), physicalAttackAction.getAttackerReference());
		assertEquals(physicalAttackAction1.getTargetKey(), physicalAttackAction.getTargetKey());

		PlaySpellCardAction playSpellCardAction1 = (PlaySpellCardAction) deserializedGameActions.get(2);
		assertNotNull(playSpellCardAction1);
		assertEquals(playSpellCardAction1.getActionType(), ActionType.SPELL);
		assertSpellsEqual(playSpellCardAction1.getSpell(), playSpellCardAction.getSpell());
		assertEquals(playSpellCardAction1.getTargetKey(), playSpellCardAction.getTargetKey());

		PlayMinionCardAction playMinionCardAction1 = (PlayMinionCardAction) deserializedGameActions.get(3);
		assertNotNull(playMinionCardAction1);
		assertEquals(playMinionCardAction1.getActionType(), ActionType.SUMMON);
		assertEquals(playMinionCardAction1.getCardReference().getCardName(), playMinionCardAction.getCardReference().getCardName());

		BattlecryAction battlecryAction1 = (BattlecryAction) deserializedGameActions.get(4);
		assertNotNull(battlecryAction1);
		assertSpellsEqual(battlecryAction1.getSpell(), elvenArcherAction.getSpell());

		HeroPowerAction heroPowerAction1 = (HeroPowerAction) deserializedGameActions.get(5);
		assertNotNull(heroPowerAction1);
		assertEquals(heroPowerAction1.getActionType(), ActionType.HERO_POWER);
		assertSpellsEqual(heroPowerAction1.getSpell(), heroPowerAction.getSpell());

		PlayWeaponCardAction playWeaponCardAction1 = (PlayWeaponCardAction) deserializedGameActions.get(6);
		assertNotNull(playWeaponCardAction1);
		assertEquals(playWeaponCardAction1.getActionType(), ActionType.EQUIP_WEAPON);
		assertEquals(playWeaponCardAction1.getCardReference().getCardName(), playWeaponCardAction.getCardReference().getCardName());

		DiscoverAction discoverAction1 = (DiscoverAction) deserializedGameActions.get(7);
		assertNotNull(discoverAction1);
		assertEquals(discoverAction1.getActionType(), ActionType.DISCOVER);
		assertEquals(discoverAction1.getSpell().getSpellClass(), discoverAction.getSpell().getSpellClass());
		assertSpellsEqual(discoverAction1.getSpell(), discoverAction.getSpell());
	}

	private void assertSpellsEqual(SpellDesc actual, SpellDesc expected) {
		for (SpellArg arg : SpellArg.values()) {
			if (expected.contains(arg)) {
				assertEquals(actual.get(arg), expected.get(arg));
			}
		}
	}
}