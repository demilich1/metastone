package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffHeroSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffHeroSpell.class);

	private final int attackBonus;
	private final int armorBonus;
	private final boolean revert;

	public BuffHeroSpell(int attackBonus, int armorBonus) {
		this(attackBonus, armorBonus, true);
	}
	
	private BuffHeroSpell(int attackBonus, int armorBonus, boolean revert) {
		this.attackBonus = attackBonus;
		this.armorBonus = armorBonus;
		this.revert = revert;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Hero hero = player.getHero();
		if (attackBonus != 0) {
			logger.debug("{} gains {} attack", hero, attackBonus);
			hero.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			logger.debug("{} gains {} armor", hero, armorBonus);
			hero.modifyArmor(+armorBonus);
		}
		
		if (revert && attackBonus != 0) {
			BuffHeroSpell debuff = new BuffHeroSpell(-attackBonus, 0, false);
			debuff.setTarget(hero.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(new TurnEndTrigger(), debuff, true);
			removeTrigger.setHost(hero);
			removeTrigger.setOwner(hero.getOwner());
			context.addTrigger(removeTrigger);
		}
	}

}
