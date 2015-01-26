package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class HumilitySpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(HumilitySpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		target.setTag(GameTag.ATTACK, 1);
		target.removeTag(GameTag.ATTACK_MULTIPLIER);
		target.removeTag(GameTag.TEMPORARY_ATTACK_BONUS);
		target.removeTag(GameTag.ATTACK_BONUS);
	}
	
}