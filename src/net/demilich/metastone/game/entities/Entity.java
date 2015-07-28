package net.demilich.metastone.game.entities;

import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.IdFactory;

public abstract class Entity extends CustomCloneable {

	private String name;
	protected Map<Attribute, Object> attributes = new EnumMap<Attribute, Object>(Attribute.class);
	private int id = IdFactory.UNASSIGNED;
	private int ownerIndex = -1;
	
	public abstract EntityType getEntityType();

	public int getId() {
		return id;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ownerIndex;
//		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Entity other = (Entity) obj;
//		if (id != other.id)
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (ownerIndex != other.ownerIndex)
//			return false;
//		if (tags == null) {
//			if (other.tags != null)
//				return false;
//		} else if (!tags.equals(other.tags))
//			return false;
//		return true;
//	}

	public String getName() {
		return name;
	}

	public int getOwner() {
		return ownerIndex;
	}

	public EntityReference getReference() {
		return EntityReference.pointTo(this);
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
	
	public boolean hasAttribute(Attribute attribute) {
		Object value = attributes.get(attribute);
		if (value == null) {
			return false;
		}
		if (value instanceof Integer) {
			return ((int)value) > 0;
		}
		return true;
	}
	
	public boolean isDestroyed() {
		return hasAttribute(Attribute.DESTROYED);
	}

	public void modifyAttribute(Attribute attribute, int value) {
		if (!hasAttribute(attribute)) {
			setAttribute(attribute, 0);
		}
		setAttribute(attribute, getAttributeValue(attribute) + value);
	}

	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute);
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

	public void setAttribute(Attribute attribute) {
		attributes.put(attribute, 1);
	}

	public void setAttribute(Attribute attribute, int value) {
		attributes.put(attribute, value);
	}

	public void setAttribute(Attribute attribute, Object value) {
		attributes.put(attribute, value);
	}

}
