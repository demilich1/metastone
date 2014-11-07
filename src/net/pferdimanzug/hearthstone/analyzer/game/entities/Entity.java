package net.pferdimanzug.hearthstone.analyzer.game.entities;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.logic.CustomCloneable;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class Entity extends CustomCloneable {

	private String name;
	protected HashMap<GameTag, Object> tags = new HashMap<GameTag, Object>();
	private int id;
	private int ownerIndex = -1;
	
	public abstract EntityType getEntityType();

	public int getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ownerIndex;
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerIndex != other.ownerIndex)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
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

	public Object getTag(GameTag tag) {
		return tags.get(tag);
	}

	public HashMap<GameTag, Object> getTags() {
		return tags;
	}

	public int getTagValue(GameTag tag) {
		return tags.containsKey(tag) ? (int) tags.get(tag) : 0;
	}

	/**
	 * Returns an unique identifier for this Entity type.
	 * This method can be used in cases where only one-of-a-kind
	 * is allowed, i.e. secrets.
	 * <p>
	 * Notable properties of the return value:
	 * if o1.getTypeId() == o2.getTypeId() then no statement about o1 == o2
	 * if o1 == o2 then o1.getTypeId() ==  o2.getTypeId()
	 *
	 * @return      unique identifier for this type of entity
	 */
	public int getTypeId() {
		return getClass().getName().hashCode();
	}

	public boolean hasStatus(GameTag tag) {
		return tags.get(tag) != null && getTagValue(tag) > 0;
	}
	
	public boolean hasTag(GameTag tag) {
		return tags.get(tag) != null;
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
