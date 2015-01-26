package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DoubleAttackSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(DoubleAttackSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor targetActor = (Actor) target;
		int attackMultiplier = targetActor.getAttackMultiplier();
		target.setTag(GameTag.ATTACK_MULTIPLIER, attackMultiplier * 2);
	}

}
