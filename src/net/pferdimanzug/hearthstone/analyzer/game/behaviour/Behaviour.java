package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

public abstract class Behaviour implements IBehaviour {

	public IBehaviour clone() {
		try {
			return (IBehaviour)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
