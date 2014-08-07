package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class Enrage extends Spell {
	
	private final int attackBonus;

	public Enrage(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackModifier = target.hasTag(GameTag.ENRAGED) ? +attackBonus : -attackBonus;
		target.modifyTag(GameTag.ATTACK_BONUS, attackModifier);
	}

}
