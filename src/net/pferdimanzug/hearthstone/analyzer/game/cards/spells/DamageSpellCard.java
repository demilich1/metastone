package net.pferdimanzug.hearthstone.analyzer.game.cards.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public abstract class DamageSpellCard extends SpellCard {
	
	private final int damage;

	public DamageSpellCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost, int damage) {
		super(name, rarity, classRestriction, manaCost);
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}

}
