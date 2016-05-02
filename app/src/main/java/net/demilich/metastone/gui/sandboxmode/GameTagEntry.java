package net.demilich.metastone.gui.sandboxmode;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.utils.GameTagUtils;
import net.demilich.metastone.game.utils.TagValueType;

public class GameTagEntry {

	private final Attribute tag;
	private final TagValueType valueType;
	private final Object value;

	public GameTagEntry(Attribute tag, Object value) {
		this.tag = tag;
		this.value = value;
		this.valueType = GameTagUtils.getTagValueType(tag);
	}

	public String getName() {
		return GameTagUtils.getTagName(getTag());
	}

	public Attribute getTag() {
		return tag;
	}

	public Object getValue() {
		return value;
	}

	public boolean getValueBool() {
		return (int) value >= 1;
	}

	public int getValueInt() {
		return (int) value;
	}

	public TagValueType getValueType() {
		return valueType;
	}

}
