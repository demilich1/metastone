package net.pferdimanzug.hearthstone.analyzer.game.aura;

import java.util.HashSet;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class Aura {
	
	private final Actor source;
	private final HashSet<Actor> affectedEntities = new HashSet<>();

	public Aura(Actor source) {
		this.source = source;
	}
	
	public abstract boolean affects(Actor entity);
	
	public void effectApply(Actor entity) {
		affectedEntities.add(entity);
		onApply(entity);
	}
	
	public void effectRemove(Entity entity) {
		for (Actor affectedEntity : affectedEntities) {
			onRemove(affectedEntity);
		}
	}
	
	public Actor getSource() {
		return source;
	}
	
	protected abstract void onApply(Actor entity);

	protected abstract void onRemove(Actor entity);

}
