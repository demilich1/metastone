package net.pferdimanzug.hearthstone.analyzer.game.aura;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
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

	@Override
	public void onAdd(GameContext context) {
		applySpellEffect(context, applyAuraEffect);
	}

	@Override
	public void onRemove(GameContext context) {
		applySpellEffect(context, removeAuraEffect);
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

	private void applySpellEffect(GameContext context, Spell spell) {
		Player owner = context.getPlayer(getOwner());
		Actor sourceActor = context.resolveSingleTarget(getOwner(), getHostReference());
		List<Actor> resolvedTargets = context.resolveTarget(owner, sourceActor, targets);
		for (Actor target : resolvedTargets) {
			if (!affects(context, target)) {
				continue;
			}
			spell.setTarget(target.getReference());
			context.getLogic().castSpell(getOwner(), spell);
		}
	}

	protected boolean affects(GameContext context, Actor target) {
		if (target.getEntityType() != EntityType.MINION) {
			return false;
		}
		if (target.getReference().equals(getHostReference())) {
			return false;
		}
		
		Player owner = context.getPlayer(getOwner());
		Actor sourceActor = context.resolveSingleTarget(getOwner(), getHostReference());
		List<Actor> validTargets = context.resolveTarget(owner, sourceActor, targets);
		if (!validTargets.contains(target)) {
			return false;
		}
		
		return true;
	}

}
