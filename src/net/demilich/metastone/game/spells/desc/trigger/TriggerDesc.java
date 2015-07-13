package net.demilich.metastone.game.spells.desc.trigger;

import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TriggerDesc {
	
	public EventTriggerDesc eventTrigger;
	//public EventTriggerDesc secondEventTrigger;
	public SpellDesc spell;
	
	public SpellTrigger create() {
		//GameEventTrigger secondaryTrigger = secondEventTrigger != null ? secondEventTrigger.create() : null;
		return new SpellTrigger(eventTrigger.create(), spell);
	}

}
