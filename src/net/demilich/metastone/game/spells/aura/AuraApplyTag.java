package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.targeting.EntityReference;

public class AuraApplyTag extends Aura {

	public AuraApplyTag(GameTag tag, EntityReference targetSelection) {
		super(AddAttributeSpell.create(tag), RemoveAttributeSpell.create(tag), targetSelection);
	}

}
