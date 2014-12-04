package net.pferdimanzug.hearthstone.analyzer.game.targeting;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class EntityReference {
	public static EntityReference pointTo(Entity entity) {
		if (entity == null) {
			return null;
		}
		return new EntityReference(entity.getId());
	}
	public static final EntityReference NONE = new EntityReference(-1);
	public static final EntityReference ENEMY_CHARACTERS = new EntityReference(-2);
	public static final EntityReference ENEMY_MINIONS = new EntityReference(-3);
	public static final EntityReference ENEMY_HERO = new EntityReference(-4);
	public static final EntityReference FRIENDLY_CHARACTERS = new EntityReference(-5);
	public static final EntityReference FRIENDLY_MINIONS = new EntityReference(-6);
	public static final EntityReference OTHER_FRIENDLY_MINIONS = new EntityReference(-7);
	public static final EntityReference ADJACENT_MINIONS = new EntityReference(-8);
	public static final EntityReference FRIENDLY_HERO = new EntityReference(-9);
	public static final EntityReference ALL_MINIONS = new EntityReference(-10);
	public static final EntityReference ALL_CHARACTERS = new EntityReference(-11);
	public static final EntityReference FRIENDLY_WEAPON = new EntityReference(-12);
	public static final EntityReference ENEMY_WEAPON = new EntityReference(-13);
	
	public static final EntityReference EVENT_TARGET = new EntityReference(-20);
	public static final EntityReference SELF = new EntityReference(-21);
	public static final EntityReference KILLED_MINION = new EntityReference(-22);
	public static final EntityReference ATTACKER = new EntityReference(-23);
	
	public static final EntityReference PENDING_CARD = new EntityReference(-24);
		
	private final int key;
	
	public EntityReference(int key) {
		this.key = key;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EntityReference)) {
			return false;
		}
		EntityReference entityReference = (EntityReference) obj;
		return entityReference.getId() == getId();
	}
	
	public int getId() {
		return key;
	}

	@Override
	public int hashCode() {
		return new Integer(key).hashCode();
	}
	
	public boolean isTargetGroup() {
		return key < 0;
	}
	
	@Override
	public String toString() {
		return String.format("[EntityReference id:%d]", key);
	}
}
