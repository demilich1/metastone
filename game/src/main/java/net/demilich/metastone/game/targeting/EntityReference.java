package net.demilich.metastone.game.targeting;

import java.io.Serializable;

import net.demilich.metastone.game.entities.Entity;

public class EntityReference implements Serializable{
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
	public static final EntityReference ALL_OTHER_CHARACTERS = new EntityReference(-12);
	public static final EntityReference ALL_OTHER_MINIONS = new EntityReference(-13);
	public static final EntityReference FRIENDLY_WEAPON = new EntityReference(-14);
	public static final EntityReference ENEMY_WEAPON = new EntityReference(-15);
	public static final EntityReference FRIENDLY_HAND = new EntityReference(-16);
	public static final EntityReference ENEMY_HAND = new EntityReference(-17);
	public static final EntityReference OPPOSITE_MINIONS = new EntityReference(-18);
	public static final EntityReference LEFTMOST_FRIENDLY_MINION = new EntityReference(-19);
	public static final EntityReference LEFTMOST_ENEMY_MINION = new EntityReference(-20);
	public static final EntityReference FRIENDLY_PLAYER = new EntityReference(-21);
	public static final EntityReference ENEMY_PLAYER = new EntityReference(-22);
	public static final EntityReference MINIONS_TO_LEFT = new EntityReference(-23);
	public static final EntityReference MINIONS_TO_RIGHT = new EntityReference(-24);

	public static final EntityReference TARGET = new EntityReference(-30);
	public static final EntityReference SPELL_TARGET = new EntityReference(-31);

	public static final EntityReference EVENT_TARGET = new EntityReference(-40);
	public static final EntityReference SELF = new EntityReference(-41);
	public static final EntityReference KILLED_MINION = new EntityReference(-42);
	public static final EntityReference ATTACKER_REFERENCE = new EntityReference(-43);
	public static final EntityReference PENDING_CARD = new EntityReference(-44);
	public static final EntityReference EVENT_CARD = new EntityReference(-45);

	public static EntityReference pointTo(Entity entity) {
		if (entity == null) {
			return null;
		}
		return new EntityReference(entity.getId());
	}

	private int id;

	public EntityReference() {}
	public EntityReference(int key) {
		this.id = key;
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
		return id;
	}
	public void setId(int key) {
		this.id = key;
	}

	@Override
	public int hashCode() {
		return new Integer(id).hashCode();
	}

	public boolean isTargetGroup() {
		return id < 0;
	}

	@Override
	public String toString() {
		return String.format("[EntityReference id:%d]", id);
	}
}
