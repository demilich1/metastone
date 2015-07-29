package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;

public abstract class ActorCardDesc extends CardDesc {

	public BattlecryDesc battlecry;
	public SpellDesc deathrattle;
	public TriggerDesc trigger;
	public AuraDesc aura;

}
