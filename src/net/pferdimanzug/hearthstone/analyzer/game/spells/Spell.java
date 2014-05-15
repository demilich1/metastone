package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class Spell {
	
	private EntityReference target;
	protected boolean applySpellpower;
	private EntityReference source;

	public void cast(GameContext context, Player player, List<Actor> targets) {
		if (targets == null) {
			onCast(context, player, null);
			return;
		}
		for (Actor target : targets) {
			onCast(context, player, target);
		}
	}

	public EntityReference getTarget() {
		return target;
	}

	public boolean hasPredefinedTarget() {
		if (target != null) {
			return target.isTargetGroup();
		}
		return false;
	}

	protected abstract void onCast(GameContext context, Player player, Actor target);
	
	public void setTarget(EntityReference target) {
		this.target = target;
	}

	public boolean applySpellpower() {
		return applySpellpower;
	}

	public void setApplySpellpower(boolean applySpellpower) {
		this.applySpellpower = applySpellpower;
	}

	public EntityReference getSource() {
		return source;
	}

	public void setSource(EntityReference source) {
		this.source = source;
	}

}
