package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class Enrage extends Spell {
	
	public static SpellDesc create(int attackBonus) {
		SpellDesc desc = new SpellDesc(Enrage.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int attackModifier = target.hasStatus(GameTag.ENRAGED) ? +attackBonus : -attackBonus;
		target.modifyTag(GameTag.ATTACK_BONUS, attackModifier);
	}

}
