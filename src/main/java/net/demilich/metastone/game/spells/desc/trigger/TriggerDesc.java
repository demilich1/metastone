package net.demilich.metastone.game.spells.desc.trigger;

import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TriggerDesc {

	public EventTriggerDesc eventTrigger;
	public SpellDesc spell;
	public boolean oneTime;

	public SpellTrigger create() {
		return new SpellTrigger(eventTrigger.create(), spell, oneTime);
	}

}
