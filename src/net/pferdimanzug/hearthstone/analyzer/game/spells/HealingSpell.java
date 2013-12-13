package net.pferdimanzug.hearthstone.analyzer.game.spells;


public abstract class HealingSpell implements ISpell {

	private final int healing;

	public HealingSpell(int healing) {
		this.healing = healing;
	}

	public int getHealing() {
		return healing;
	}

}
