package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class Spell implements Cloneable {

	private EntityReference target;
	private SpellSource source;
	private EntityReference sourceEntity;

	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (targets == null) {
			onCast(context, player, null);
			return;
		}
		for (Entity target : targets) {
			onCast(context, player, target);
		}
	}

	@Override
	public Spell clone() {
		try {
			return (Spell) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> T getRandomTarget(List<T> targets) {
		int randomIndex = ThreadLocalRandom.current().nextInt(targets.size());
		return targets.get(randomIndex);
	}

	public SpellSource getSource() {
		return source;
	}

	public EntityReference getSourceEntity() {
		return sourceEntity;
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

	protected abstract void onCast(GameContext context, Player player, Entity target);

	public void setSource(SpellSource source) {
		this.source = source;
	}

	public void setSourceEntity(EntityReference sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	public void setTarget(EntityReference target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "[SPELL " + getClass().getSimpleName() + " target=" + target + ", source=" + source
				+ "]";
	}

}
