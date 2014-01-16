package net.pferdimanzug.hearthstone.analyzer.game.targeting;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class EntityReference {
	public static final EntityReference NONE = new EntityReference(-1);
	public static final EntityReference ENEMY_CHARACTERS = new EntityReference(-2);
	public static final EntityReference ENEMY_MINIONS = new EntityReference(-3);
	public static final EntityReference ENEMY_HERO = new EntityReference(-4);
	public static final EntityReference FRIENDLY_CHARACTERS = new EntityReference(-5);
	public static final EntityReference FRIENDLY_MINIONS = new EntityReference(-6);
	public static final EntityReference FRIENDLY_HERO = new EntityReference(-7);
	public static final EntityReference ALL_MINIONS = new EntityReference(-8);
	public static final EntityReference ALL_CHARACTERS = new EntityReference(-9);
	
	public static EntityReference pointTo(Entity entity) {
		return new EntityReference(entity.getId());
	}
		
	private final int key;
	
	private EntityReference(int key) {
		this.key = key;
	}
	
	public int getId() {
		return key;
	}
	
	public boolean isTargetGroup() {
		return key < 0;
	}
}
