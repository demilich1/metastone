package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffHeroSpell extends RevertableSpell {

	private static Logger logger = LoggerFactory.getLogger(BuffHeroSpell.class);

	private final int attackBonus;
	private final int armorBonus;

	public BuffHeroSpell(int attackBonus, int armorBonus) {
		this(attackBonus, armorBonus, true);
	}
	
	private BuffHeroSpell(int attackBonus, int armorBonus, boolean revert) {
		super(revert ? new TurnEndTrigger() :null);
		this.attackBonus = attackBonus;
		this.armorBonus = armorBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Hero hero = player.getHero();
		if (attackBonus != 0) {
			logger.debug("{} gains {} attack", hero, attackBonus);
			hero.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			logger.debug("{} gains {} armor", hero, armorBonus);
			hero.modifyArmor(+armorBonus);
		}
		
		if (attackBonus != 0) {
			super.onCast(context, player, target);
		}
	}

	@Override
	protected Spell getReverseSpell() {
		return new BuffHeroSpell(-attackBonus, 0, false);
	}

}
