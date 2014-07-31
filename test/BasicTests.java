import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.TheCoin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Malfurion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicTests extends TestBase {
	
	private TheCoin getTheCoin(CardCollection cards) {
		for (Card card : cards) {
			if (card instanceof TheCoin) {
				return (TheCoin) card;
			}
		}
		return null;
	}
	
	@Test
	public void testBattlecry() { 
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		TestMinionCard devMonster = new TestMinionCard(3, 3);
		Spell damageSpell = new DamageSpell(3);
		damageSpell.setTarget(EntityReference.ENEMY_HERO);
		Battlecry testBattlecry = Battlecry.createBattlecry(damageSpell);
		testBattlecry.setTarget(warrior.getHero());
		devMonster.getMinion().setBattlecry(testBattlecry);
		context.getLogic().receiveCard(mage.getId(), devMonster);
		context.getLogic().performGameAction(mage.getId(), devMonster.play());
		
		Assert.assertEquals(warrior.getHero().getHp(), warrior.getHero().getMaxHp() - 3);
	}

	@Test
	public void testHeroAttack() { 
		GameContext context = createContext(new Jaina(), new Malfurion());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player druid = context.getPlayer2();
		druid.setMana(10);

		int damage = 1;
		TestMinionCard devMonsterCard = new TestMinionCard(damage, 2);
		mage.getHand().add(devMonsterCard);
		context.getLogic().performGameAction(mage.getId(), devMonsterCard.play());
		
		BuffHeroSpell heroBuffSpell = new BuffHeroSpell(damage, 0);
		context.getLogic().castSpell(druid.getId(), heroBuffSpell);
		context.getLogic().endTurn(druid.getId());
		
		Actor devMonster = getSingleMinion(mage.getMinions());
		GameAction minionAttackAction = new PhysicalAttackAction(devMonster.getReference());
		minionAttackAction.setTarget(druid.getHero());
		context.getLogic().performGameAction(mage.getId(), minionAttackAction);
		// monster attacked; it should not be damaged by the hero
		Assert.assertEquals(druid.getHero().getHp(), druid.getHero().getMaxHp() - damage);
		Assert.assertEquals(devMonster.getHp(), devMonster.getMaxHp());
		context.getLogic().endTurn(mage.getId());
		
		context.getLogic().castSpell(druid.getId(), heroBuffSpell);
		GameAction heroAttackAction = new PhysicalAttackAction(druid.getHero().getReference());
		heroAttackAction.setTarget(devMonster);
		context.getLogic().performGameAction(mage.getId(), heroAttackAction);
		// hero attacked; both entities should be damaged
		Assert.assertEquals(druid.getHero().getHp(), druid.getHero().getMaxHp() - 2 * damage);
		Assert.assertEquals(devMonster.getHp(), devMonster.getMaxHp() - damage);
	}

	@Test
	public void testMinionAttack() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		MinionCard minionCard1 = new TestMinionCard(5, 5);
		context.getLogic().receiveCard(mage.getId(), minionCard1);
		context.getLogic().performGameAction(mage.getId(), minionCard1.play());
		
		MinionCard minionCard2 = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(warrior.getId(), minionCard2);
		context.getLogic().performGameAction(warrior.getId(), minionCard2.play());
		
		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 1);
		
		Actor attacker = getSingleMinion(mage.getMinions());
		Actor defender = getSingleMinion(warrior.getMinions());
		
		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		attackAction.setTarget(defender);
		context.getLogic().performGameAction(mage.getId(), attackAction);
		
		Assert.assertEquals(attacker.getHp(), attacker.getMaxHp() - defender.getAttack());
		Assert.assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		Assert.assertEquals(defender.isDead(), true);
		
		Assert.assertEquals(mage.getMinions().size(), 1);
		Assert.assertEquals(warrior.getMinions().size(), 0);
	}
	
	@Test
	public void testSummon() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		mage.getHand().removeAll();
		MinionCard devMonster = new TestMinionCard(1, 1);
		context.getLogic().receiveCard(mage.getId(), devMonster);
		Assert.assertEquals(mage.getHand().getCount(), 1);
		context.getLogic().performGameAction(mage.getId(), devMonster.play());
		Assert.assertEquals(mage.getHand().isEmpty(), true);
		Actor minion = getSingleMinion(mage.getMinions());
		Assert.assertEquals(minion.getName(), devMonster.getName());
		Assert.assertEquals(minion.getAttack(), 1);
		Assert.assertEquals(minion.getHp(), 1);
		Assert.assertEquals(minion.isDead(), false);
		
		MinionCard devMonster2 = new TestMinionCard(2, 2);
		context.getLogic().receiveCard(mage.getId(), devMonster2);
		GameAction summonAction = devMonster2.play();
		summonAction.setTarget(minion);
		context.getLogic().performGameAction(mage.getId(), summonAction);
		
		Assert.assertEquals(mage.getMinions().size(), 2);
		Actor left = mage.getMinions().get(0);
		Actor right = mage.getMinions().get(1);
		Assert.assertEquals(left.getAttack(), 2);
		Assert.assertEquals(right.getAttack(), 1);
	}
	
	@Test
	public void testTheCoin() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Player mage = context.getPlayer1();
		Player warrior = context.getPlayer2();
		
		TheCoin theCoin = getTheCoin(mage.getHand());
		Assert.assertEquals(theCoin, null);
		theCoin = getTheCoin(warrior.getHand());
		Assert.assertNotEquals(theCoin, null);
	}
	
	@Test
	public void testWeapon() { 
		DebugContext context = createContext(new Garrosh(), new Garrosh());
		Player player = context.getPlayer1();
		Hero warrior = player.getHero();
		
		WeaponCard weaponCard = new WeaponCard("Test Weapon", Rarity.FREE, HeroClass.ANY, 0) {
			
			@Override
			public Weapon getWeapon() {
				return createWeapon(2, 2);
			}
		};
		
		context.setActivePlayer(player.getId());
		context.getLogic().startTurn(player.getId());
		Assert.assertEquals(warrior.getAttack(), 0);
		context.getLogic().receiveCard(player.getId(), weaponCard);
		context.getLogic().performGameAction(player.getId(), weaponCard.play());
		Assert.assertEquals(warrior.getAttack(), 2);
		Assert.assertEquals(warrior.getWeapon().getDurability(), 2);
		
		PhysicalAttackAction attack = new PhysicalAttackAction(warrior.getReference());
		attack.setTarget(context.getPlayer2().getHero());
		context.getLogic().performGameAction(player.getId(), attack);
		Assert.assertEquals(warrior.getWeapon().getDurability(), 1);
	}

}
