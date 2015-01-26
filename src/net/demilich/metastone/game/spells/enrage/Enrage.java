package net.demilich.metastone.game.spells.enrage;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		target.modifyTag(GameTag.ATTACK, attackModifier);
	}

}
