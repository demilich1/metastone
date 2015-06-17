package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;

public class AttributeAura extends Aura {

	public AttributeAura(AuraDesc desc) {
		super(AddAttributeSpell.create(desc.getAttribute()), RemoveAttributeSpell.create(desc.getAttribute()), desc.getTarget());
	}

}
