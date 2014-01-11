package net.pferdimanzug.hearthstone.analyzer.game.aura;

import java.util.HashSet;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public abstract class Aura {
	
	private final Entity source;
	private final HashSet<Entity> affectedEntities = new HashSet<>();

	public Aura(Entity source) {
		this.source = source;
	}
	
	public abstract boolean affects(Entity entity);
	public void effectApply(Entity entity) {
		affectedEntities.add(entity);
		onApply(entity);
	}
	public void effectRemove(Entity entity) {
		for (Entity affectedEntity : affectedEntities) {
			onRemove(affectedEntity);
		}
	}
	public Entity getSource() {
		return source;
	}
	
	protected abstract void onApply(Entity entity);

	protected abstract void onRemove(Entity entity);

}
