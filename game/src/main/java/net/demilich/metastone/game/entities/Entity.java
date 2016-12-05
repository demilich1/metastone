package net.demilich.metastone.game.entities;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.IdFactory;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Entity extends CustomCloneable implements Serializable {

	private String name;
	protected Map<Attribute, Object> attributes = new EnumMap<Attribute, Object>(Attribute.class);
	private int id = IdFactory.UNASSIGNED;
	private int ownerIndex = -1;

	protected Entity() {
	}

	@Override
	public Entity clone() {
		Entity clone = (Entity) super.clone();
		return clone;
	}

	public Object getAttribute(Attribute attribute) {
		return attributes.get(attribute);
	}

	public Map<Attribute, Object> getAttributes() {
		return attributes;
	}

	public int getAttributeValue(Attribute attribute) {
		return attributes.containsKey(attribute) ? (int) attributes.get(attribute) : 0;
	}

	public abstract EntityType getEntityType();

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getOwner() {
		return ownerIndex;
	}

	public EntityReference getReference() {
		return EntityReference.pointTo(this);
	}

	public boolean hasAttribute(Attribute attribute) {
		Object value = attributes.get(attribute);
		if (value == null) {
			return false;
		}
		if (value instanceof Integer) {
			return ((int) value) != 0;
		}
		return true;
	}

	public boolean isDestroyed() {
		return hasAttribute(Attribute.DESTROYED);
	}

	public void modifyAttribute(Attribute attribute, int value) {
		if (!attributes.containsKey(attribute)) {
			setAttribute(attribute, 0);
		}
		setAttribute(attribute, getAttributeValue(attribute) + value);
	}

	public void modifyHpBonus(int value) {
		modifyAttribute(Attribute.HP_BONUS, value);
	}

	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute);
	}

	public void setAttribute(Attribute attribute) {
		attributes.put(attribute, 1);
	}

	public void setAttribute(Attribute attribute, int value) {
		attributes.put(attribute, value);
	}

	public void setAttribute(Attribute attribute, Object value) {
		attributes.put(attribute, value);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}
}
