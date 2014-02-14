package net.pferdimanzug.hearthstone.analyzer.game.entities;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class Entity implements Cloneable {

	private String name;
	protected HashMap<GameTag, Object> tags = new HashMap<GameTag, Object>();
	private int id;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EntityReference getReference() {
		return EntityReference.pointTo(this);
	}

	public Object getTag(GameTag tag) {
		return tags.get(tag);
	}

	public int getTagValue(GameTag tag) {
		return tags.containsKey(tag) ? (int) tags.get(tag) : 0;
	}

	public boolean hasTag(GameTag tag) {
		return tags.get(tag) != null;
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

	public void setTag(GameTag tag) {
		tags.put(tag, 1);
	}

	public void setTag(GameTag tag, int value) {
		tags.put(tag, value);
	}

	public void setTag(GameTag tag, Object value) {
		tags.put(tag, value);
	}

	public HashMap<GameTag, Object> getTags() {
		return tags;
	}

}
