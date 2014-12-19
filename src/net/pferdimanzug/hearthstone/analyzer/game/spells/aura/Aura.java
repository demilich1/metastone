package net.pferdimanzug.hearthstone.analyzer.game.spells.aura;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Aura extends SpellTrigger {

	private EntityReference targets;
	private SpellDesc applyAuraEffect;
	private SpellDesc removeAuraEffect;
	private HashSet<Integer> affectedEntities = new HashSet<>();

	public Aura(SpellDesc applyAuraEffect, SpellDesc removeAuraEffect, EntityReference targetSelection) {
		super(new BoardChangedTrigger(), applyAuraEffect);
		this.applyAuraEffect = applyAuraEffect;
		this.removeAuraEffect = removeAuraEffect;
		this.targets = targetSelection;
	}

	protected boolean affects(GameContext context, Entity target, List<Entity> resolvedTargets) {
		if (target.getEntityType() != EntityType.MINION) {
			return false;
		}
		if (target.getReference().equals(getHostReference())) {
			return false;
		}

		Actor targetActor = (Actor) target;
		if (targetActor.isDead()) {
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
		for (int entityId : affectedEntities) {
			EntityReference entityReference = new EntityReference(entityId);
			Entity affectedEntity = context.resolveSingleTarget(entityReference);
			relevantTargets.add(affectedEntity);
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

}
