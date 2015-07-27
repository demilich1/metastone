package net.demilich.metastone.game.entities;

import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.IdFactory;

public abstract class Entity extends CustomCloneable {

	private String name;
	protected Map<GameTag, Object> tags = new EnumMap<GameTag, Object>(GameTag.class);
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

	public Object getTag(GameTag tag) {
		return tags.get(tag);
	}

	public Map<GameTag, Object> getTags() {
		return tags;
	}

	public int getTagValue(GameTag tag) {
		return tags.containsKey(tag) ? (int) tags.get(tag) : 0;
	}
	
	public boolean hasTag(GameTag tag) {
		Object value = tags.get(tag);
		if (value == null) {
			return false;
		}
		if (value instanceof Integer) {
			return ((int)value) > 0;
		}
		return true;
	}
	
	public boolean isDestroyed() {
		return hasTag(GameTag.DESTROYED);
	}

	public void modifyTag(GameTag tag, int value) {
		if (!hasTag(tag)) {
			setTag(tag, 0);
		}
		setTag(tag, getTagValue(tag) + value);
	}

	public void removeTag(GameTag tag) {
		tags.remove(tag);
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

	public void setTag(GameTag tag) {
		tags.put(tag, 1);
	}

	public void setTag(GameTag tag, int value) {
		tags.put(tag, value);
	}

	public void setTag(GameTag tag, Object value) {
		tags.put(tag, value);
	}

}
