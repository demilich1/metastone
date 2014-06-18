package net.pferdimanzug.hearthstone.analyzer.game.aura;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Aura extends SpellTrigger {

	private final EntityReference targets;
	private final Spell applyAuraEffect;
	private final Spell removeAuraEffect;

	public Aura(Spell applyAuraEffect, Spell removeAuraEffect, EntityReference targetSelection) {
		super(new MinionSummonedTrigger(), applyAuraEffect);
		this.applyAuraEffect = applyAuraEffect;
		this.removeAuraEffect = removeAuraEffect;
		this.targets = targetSelection;
	}

	protected boolean affects(GameContext context, Entity target) {
		if (target.getEntityType() != EntityType.MINION) {
			return false;
		}
		if (target.getReference().equals(getHostReference())) {
			return false;
		}
		
		Player owner = context.getPlayer(getOwner());
		Actor sourceActor = (Actor) context.resolveSingleTarget(getOwner(), getHostReference());
		List<Entity> validTargets = context.resolveTarget(owner, sourceActor, targets);
		if (!validTargets.contains(target)) {
			return false;
		}
		
		return true;
	}

	private void applySpellEffect(GameContext context, Spell spell) {
		Player owner = context.getPlayer(getOwner());
		Actor sourceActor = (Actor) context.resolveSingleTarget(getOwner(), getHostReference());
		List<Entity> resolvedTargets = context.resolveTarget(owner, sourceActor, targets);
		for (Entity target : resolvedTargets) {
			if (!affects(context, target)) {
				continue;
			}
			spell.setTarget(target.getReference());
			context.getLogic().castSpell(getOwner(), spell);
		}
	}

	@Override
	public void onAdd(GameContext context) {
		applySpellEffect(context, applyAuraEffect);
	}

	public void onGameEvent(GameEvent event) {
		SummonEvent summonEvent = (SummonEvent) event;
		Actor target = summonEvent.getMinion();
		if (!affects(summonEvent.getGameContext(), target)) {
			return;
		}
		applyAuraEffect.setTarget(target.getReference());
		event.getGameContext().getLogic().castSpell(getOwner(), applyAuraEffect);
	}

	@Override
	public void onRemove(GameContext context) {
		applySpellEffect(context, removeAuraEffect);
	}

}
