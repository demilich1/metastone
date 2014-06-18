package net.pferdimanzug.hearthstone.analyzer.game.aura;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuraSpellBuff extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	private int attackBonus;
	private int hpBonus;

	public AuraSpellBuff(int attackBonus) {
		this(attackBonus, 0);
	}

	public AuraSpellBuff(int attackBonus, int hpBonus) {
		this.attackBonus = attackBonus;
		this.hpBonus = hpBonus;
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public int getHpBonus() {
		return hpBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Actor targetActor = (Actor) target;
		logger.debug("{} gains ({} from aura effect)", targetActor, attackBonus + "/" + hpBonus);
		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.AURA_ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyAuraHpBonus(hpBonus);
		}

		
	}
}
