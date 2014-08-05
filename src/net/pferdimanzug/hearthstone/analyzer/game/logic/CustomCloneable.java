package net.pferdimanzug.hearthstone.analyzer.game.logic;

public class CustomCloneable implements Cloneable {

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
