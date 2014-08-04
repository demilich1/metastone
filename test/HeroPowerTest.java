import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Anduin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Guldan;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.ArmorUp;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Fireblast;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.LesserHeal;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.LifeTap;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellSource;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HeroPowerTest extends TestBase {

	@Test
	public void testArmorUp() {
		GameContext context = createContext(new Garrosh(), new Jaina());
		final Hero warrior = context.getPlayer1().getHero();
		
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP);
		
		GameAction armorUp = warrior.getHeroPower().play();
		context.getLogic().performGameAction(context.getPlayer1().getId(), armorUp);
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP);
		Assert.assertEquals(warrior.getArmor(), ArmorUp.ARMOR_BONUS);
		
		GameAction damage = new GameAction() {
			
			@Override
			public void execute(GameContext context, int playerId) {
				context.getLogic().damage(context.getPlayer1(), warrior, 2 * ArmorUp.ARMOR_BONUS, SpellSource.SPELL_TRIGGER);
			}

			@Override
			public boolean isSameActionGroup(GameAction anotherAction) {
				return false;
			}
		};
		damage.setTarget(warrior);
		context.getLogic().performGameAction(context.getPlayer2().getId(), damage);
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP - ArmorUp.ARMOR_BONUS);
		Assert.assertEquals(warrior.getArmor(), 0);
		
		// there was a bug where armor actually increased the hp of the hero when
		// the damage dealt was less than the total armor. Following test
		// covers that scenario
		context.getLogic().performGameAction(context.getPlayer1().getId(), armorUp);
        damage = new GameAction() {
			
			@Override
			public void execute(GameContext context, int playerId) {
				context.getLogic().damage(context.getPlayer1(), warrior, ArmorUp.ARMOR_BONUS / 2, SpellSource.SPELL_TRIGGER);
			}

			@Override
			public boolean isSameActionGroup(GameAction anotherAction) {
				return false;
			}
		};
		damage.setTarget(warrior);
		context.getLogic().performGameAction(context.getPlayer2().getId(), damage);
		Assert.assertEquals(warrior.getHp(), GameLogic.MAX_HERO_HP - ArmorUp.ARMOR_BONUS);
		Assert.assertEquals(warrior.getArmor(), ArmorUp.ARMOR_BONUS / 2);
	}
	
	@Test
	public void testFireblast() {
		GameContext context = createContext(new Jaina(), new Garrosh());
		Hero mage = context.getPlayer1().getHero();
		Hero victim = context.getPlayer2().getHero();
		Assert.assertEquals(victim.getHp(), GameLogic.MAX_HERO_HP);
		
		GameAction fireblast = mage.getHeroPower().play();
		fireblast.setTarget(victim);
		context.getLogic().performGameAction(context.getPlayer1().getId(), fireblast);
		Assert.assertEquals(victim.getHp(), GameLogic.MAX_HERO_HP - Fireblast.DAMAGE);
	}
	
	@Test
	public void testLesserHeal() {
		GameContext context = createContext(new Anduin(), new Garrosh());
		Hero priest = context.getPlayer1().getHero();
		
		priest.setHp(GameLogic.MAX_HERO_HP - LesserHeal.HEALING);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP - LesserHeal.HEALING);
		
		GameAction lesserHeal = priest.getHeroPower().play();
		lesserHeal.setTarget(priest);
		context.getLogic().performGameAction(context.getPlayer1().getId(), lesserHeal);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP);
		context.getLogic().performGameAction(context.getPlayer1().getId(), lesserHeal);
		Assert.assertEquals(priest.getHp(), GameLogic.MAX_HERO_HP);
	}
	
	@Test
	public void testLifeTap() {
		GameContext context = createContext(new Guldan(), new Garrosh());
		Player warlockPlayer = context.getPlayer1();
		Hero warlock = warlockPlayer.getHero();
		
		Assert.assertEquals(warlock.getHp(), GameLogic.MAX_HERO_HP);
		
		int cardCount = warlockPlayer.getHand().getCount();
		GameAction lifetap = warlock.getHeroPower().play();
		context.getLogic().performGameAction(warlockPlayer.getId(), lifetap);
		Assert.assertEquals(warlock.getHp(), GameLogic.MAX_HERO_HP - LifeTap.DAMAGE);
		Assert.assertEquals(warlockPlayer.getHand().getCount(), cardCount + 1);
	}
}
