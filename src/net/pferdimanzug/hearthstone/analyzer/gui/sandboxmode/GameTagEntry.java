package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.utils.GameTagUtils;
import net.pferdimanzug.hearthstone.analyzer.game.utils.TagValueType;

public class GameTagEntry {

	private final GameTag tag;
	private final TagValueType valueType;
	private final Object value;

	public GameTagEntry(GameTag tag, Object value) {
		this.tag = tag;
		this.value = value;
		this.valueType = GameTagUtils.getTagValueType(tag);
	}

	public String getName() {
		return GameTagUtils.getTagName(getTag());
	}

	public GameTag getTag() {
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
