package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ClearOverloadSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ClearOverloadSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int lockedMana = player.getLockedMana();
		if (lockedMana > 0) {
			context.getLogic().modifyCurrentMana(player.getId(), lockedMana);	
		}
		
		player.getHero().removeTag(GameTag.OVERLOAD);
	}

}
