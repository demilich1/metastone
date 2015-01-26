package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RemoveWindfurySpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(RemoveWindfurySpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		if (!target.hasStatus(GameTag.WINDFURY)) {
			return;
		}
		target.removeTag(GameTag.WINDFURY);
		target.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
	}

}
