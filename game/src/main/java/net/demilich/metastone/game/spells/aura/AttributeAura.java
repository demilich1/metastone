package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.desc.aura.AuraArg;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class AttributeAura extends Aura {

	public AttributeAura(AuraDesc desc) {
		super(null, AddAttributeSpell.create(desc.getAttribute()), RemoveAttributeSpell.create(desc.getAttribute()), desc.getTarget(), (EntityFilter) desc.get(AuraArg.FILTER));
	}

}
