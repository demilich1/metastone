package net.pferdimanzug.hearthstone.analyzer.game.spells;

public abstract class DamageSpell implements ISpell {

	private final int damage;

	public DamageSpell(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

}
