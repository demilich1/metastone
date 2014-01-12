package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;

public abstract class Spell {
	
	private TargetKey target;

	public Spell(TargetKey target) {
		this.setTarget(target);
	}
	
	public Spell() {
		this(null);
	}

	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (targets == null) {
			onCast(context, player, null);
			return;
		}
		for (Entity target : targets) {
			onCast(context, player, target);
		}
	}

	protected abstract void onCast(GameContext context, Player player, Entity target);

	public TargetKey getTarget() {
		return target;
	}

	public void setTarget(TargetKey target) {
		this.target = target;
	}
	
	public boolean hasPredefinedTarget() {
		if (target != null) {
			return target.isTargetGroup();
		}
		return false;
	}

}
