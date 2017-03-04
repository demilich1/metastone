package net.demilich.metastone.game.spells.desc.trigger;

import java.io.Serializable;

import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TriggerDesc implements Serializable {

	public EventTriggerDesc eventTrigger;
	public SpellDesc spell;
	public boolean oneTurn;
	public boolean persistentOwner;
	public int turnDelay;

	public SpellTrigger create() {
		SpellTrigger trigger = new SpellTrigger(eventTrigger.create(), spell, oneTurn, turnDelay);
		trigger.setPersistentOwner(persistentOwner);
		return trigger;
	}

}
