package net.pferdimanzug.hearthstone.analyzer.game.targeting;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class TargetKey {
	public static final TargetKey NONE = new TargetKey(-1);
	public static final TargetKey ENEMY_CHARACTERS = new TargetKey(-2);
	public static final TargetKey ENEMY_MINIONS = new TargetKey(-3);
	public static final TargetKey ENEMY_HERO = new TargetKey(-4);
	public static final TargetKey FRIENDLY_CHARACTERS = new TargetKey(-5);
	public static final TargetKey FRIENDLY_MINIONS = new TargetKey(-6);
	public static final TargetKey FRIENDLY_HERO = new TargetKey(-7);
	public static final TargetKey ALL_MINIONS = new TargetKey(-8);
	public static final TargetKey ALL_CHARACTERS = new TargetKey(-9);
	
	public static TargetKey pointTo(Entity entity) {
		return new TargetKey(entity.getId());
	}
		
	private final int key;
	
	private TargetKey(int key) {
		this.key = key;
	}
	
	public int getId() {
		return key;
	}
	
	public boolean isTargetGroup() {
		return key < 0;
	}
}
