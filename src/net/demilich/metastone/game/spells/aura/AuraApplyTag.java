package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.RemoveTagSpell;
import net.demilich.metastone.game.targeting.EntityReference;

public class AuraApplyTag extends Aura {

	public AuraApplyTag(GameTag tag, EntityReference targetSelection) {
		super(ApplyTagSpell.create(tag), RemoveTagSpell.create(tag), targetSelection);
	}

}
