package net.demilich.metastone.game.spells.aura;

import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class EnrageAura extends Aura {

	private boolean active;

	public EnrageAura(AuraDesc desc) {
		this(desc.getApplyEffect(), desc.getRemoveEffect(), desc.getTarget());
	}

	private EnrageAura(SpellDesc applyAuraEffect, SpellDesc removeAuraEffect, EntityReference targetSelection) {
		super(new EnrageChangedTrigger(), applyAuraEffect, removeAuraEffect, targetSelection);
	}

	@Override
	protected boolean affects(GameContext context, Entity target, List<Entity> resolvedTargets) {
		return active && super.affects(context, target, resolvedTargets);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (event.getEventType() == GameEventType.ENRAGE_CHANGED) {
			active = event.getEventTarget().hasAttribute(Attribute.ENRAGED);
		}
		super.onGameEvent(event);
	}

}
