package net.pferdimanzug.hearthstone.analyzer.game.aura;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Aura {
	
	private final Spell auraEffect;
	private EntityReference source;
	private int ownerId;
	private TargetSelection targetSelection;

	public Aura(Spell auraEffect, Spell removeAuraEffect, TargetSelection targetSelection) {
		this.auraEffect = auraEffect;
		this.setTargetSelection(targetSelection);
	}

	public Spell getAuraEffect() {
		return auraEffect;
	}

	public EntityReference getSource() {
		return source;
	}

	public void setSource(EntityReference source) {
		this.source = source;
	}
	
	public void applyIfApplicable(GameContext context, Actor target) {
		if (!affects(context, target)) {
			return;
		}
		auraEffect.setTarget(target.getReference());
		context.getLogic().castSpell(getOwnerId(), auraEffect);
	}
	
	protected boolean affects(GameContext context, Actor actor) {
		if (actor.getEntityType() != EntityType.MINION) {
			return false;
		}
		if (actor.getReference().equals(source)) {
			return false;
		}
		return true;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public TargetSelection getTargetSelection() {
		return targetSelection;
	}

	public void setTargetSelection(TargetSelection targetSelection) {
		this.targetSelection = targetSelection;
	}

	

	
}
