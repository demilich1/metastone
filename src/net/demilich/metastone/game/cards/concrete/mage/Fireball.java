package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Fireball extends SpellCard {
	
	public Fireball() {
		super("Fireball", Rarity.FREE, HeroClass.MAGE, 4);
		setDescription("Deal $6 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(6));
	}

	@Override
	public int getTypeId() {
		return 59;
	}
}
