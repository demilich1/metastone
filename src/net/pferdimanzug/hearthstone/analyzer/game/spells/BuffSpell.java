package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	private int attackBonus;
	private int hpBonus;
	private boolean temporary;

	public BuffSpell(int attackBonus) {
		this(attackBonus, 0);
	}

	public BuffSpell(int attackBonus, int hpBonus) {
		this.attackBonus = attackBonus;
		this.hpBonus = hpBonus;
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public int getHpBonus() {
		return hpBonus;
	}

	public boolean isTemporary() {
		return temporary;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);
		
		Actor targetActor = (Actor) target;
		
		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyHpBonus(+hpBonus);
		}

		if (isTemporary()) {
			BuffSpell debuff = new BuffSpell(-attackBonus, -hpBonus);
			debuff.setTarget(target.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(new TurnEndTrigger(), debuff, true);
			context.getLogic().addSpellTrigger(player, removeTrigger, target);
		}
	}

	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	public void setHpBonus(int hpBonus) {
		this.hpBonus = hpBonus;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

}
