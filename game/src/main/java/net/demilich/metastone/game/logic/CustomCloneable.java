package net.demilich.metastone.game.logic;

import java.io.Serializable;

public class CustomCloneable implements Cloneable, Serializable {

	@Override
	public CustomCloneable clone() {
		try {
			return (CustomCloneable) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
