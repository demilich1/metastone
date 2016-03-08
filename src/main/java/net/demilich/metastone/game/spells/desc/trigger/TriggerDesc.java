package net.demilich.metastone.game.spells.desc.trigger;

import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TriggerDesc {

	public EventTriggerDesc eventTrigger;
	public SpellDesc spell;
	public boolean oneTurn;
	public boolean persistentOwner;

	public SpellTrigger create() {
		SpellTrigger trigger = new SpellTrigger(eventTrigger.create(), spell, oneTurn);
		trigger.setPersistentOwner(persistentOwner);
		return trigger;
	}

}
