package net.demilich.metastone.game.spells.aura;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class EnrageAura extends Aura {
	
	private boolean active;

	public EnrageAura(SpellDesc applyAuraEffect, SpellDesc removeAuraEffect, EntityReference targetSelection) {
		super(new EnrageChangedTrigger(), applyAuraEffect, removeAuraEffect, targetSelection);
	}

	@Override
	protected boolean affects(GameContext context, Entity target, List<Entity> resolvedTargets) {
		return active && super.affects(context, target, resolvedTargets);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (event.getEventType() == GameEventType.ENRAGE_CHANGED) {
			active = event.getEventTarget().hasStatus(GameTag.ENRAGED);
		}
		super.onGameEvent(event);
	}
	
	
	
	
	

}
