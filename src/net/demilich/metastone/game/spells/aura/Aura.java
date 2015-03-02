package net.demilich.metastone.game.spells.aura;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.BoardChangedTrigger;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Aura extends SpellTrigger {

	private EntityReference targets;
	private SpellDesc applyAuraEffect;
	private SpellDesc removeAuraEffect;
	private Race raceRestriction;

	private HashSet<Integer> affectedEntities = new HashSet<>();

	public Aura(SpellDesc applyAuraEffect, SpellDesc removeAuraEffect, EntityReference targetSelection) {
		this(null, applyAuraEffect, removeAuraEffect, targetSelection);
	}

	public Aura(GameEventTrigger secondaryTrigger, SpellDesc applyAuraEffect, SpellDesc removeAuraEffect, EntityReference targetSelection) {
		super(new BoardChangedTrigger(), secondaryTrigger, applyAuraEffect, false);
		this.applyAuraEffect = applyAuraEffect;
		this.removeAuraEffect = removeAuraEffect;
		this.targets = targetSelection;
	}

	protected boolean affects(GameContext context, Entity target, List<Entity> resolvedTargets) {
		if (target.getReference().equals(getHostReference())) {
			return false;
		}

		Actor targetActor = (Actor) target;
		if (targetActor.isDead()) {
			return false;
		}
		if (getRaceRestriction() != null && target.getTag(GameTag.RACE) != getRaceRestriction()) {
			return false;
		}
		return resolvedTargets.contains(target);
	}

	@Override
	public Aura clone() {
		Aura clone = (Aura) super.clone();
		clone.targets = this.targets;
		clone.applyAuraEffect = this.applyAuraEffect.clone();
		clone.removeAuraEffect = this.removeAuraEffect.clone();
		clone.affectedEntities = new HashSet<>(this.affectedEntities);
		return clone;
	}

	public void onGameEvent(GameEvent event) {
		GameContext context = event.getGameContext();
		Player owner = context.getPlayer(getOwner());
		Actor sourceActor = (Actor) context.resolveSingleTarget(getHostReference());
		List<Entity> resolvedTargets = context.resolveTarget(owner, sourceActor, targets);
		List<Entity> relevantTargets = new ArrayList<Entity>(resolvedTargets);
		for (Iterator<Integer> iterator = affectedEntities.iterator(); iterator.hasNext();) {
			int entityId = iterator.next();
			
			EntityReference entityReference = new EntityReference(entityId);
			Entity affectedEntity = context.tryFind(entityReference);
			if (affectedEntity == null) {
				iterator.remove();
			} else {
				relevantTargets.add(affectedEntity);	
			}
		}

		for (Entity target : relevantTargets) {
			if (affects(context, target, resolvedTargets) && !affectedEntities.contains(target.getId())) {
				applyAuraEffect.setTarget(target.getReference());
				applyAuraEffect.setSourceEntity(getHostReference());
				context.getLogic().castSpell(getOwner(), applyAuraEffect);
				affectedEntities.add(target.getId());
				// target is not affected anymore, remove effect
			} else if (!affects(context, target, resolvedTargets) && affectedEntities.contains(target.getId())) {
				removeAuraEffect.setTarget(target.getReference());
				removeAuraEffect.setSourceEntity(getHostReference());
				context.getLogic().castSpell(getOwner(), removeAuraEffect);
				affectedEntities.remove(target.getId());
			}
		}
	}

	@Override
	public void onRemove(GameContext context) {
		for (int targetId : affectedEntities) {
			EntityReference targetKey = new EntityReference(targetId);
			Entity target = context.resolveSingleTarget(targetKey);
			removeAuraEffect.setTarget(target.getReference());
			removeAuraEffect.setSourceEntity(getHostReference());
			context.getLogic().castSpell(getOwner(), removeAuraEffect);
		}
		affectedEntities.clear();
	}

	public Race getRaceRestriction() {
		return raceRestriction;
	}

	public void setRaceRestriction(Race raceRestriction) {
		this.raceRestriction = raceRestriction;
	}

}
