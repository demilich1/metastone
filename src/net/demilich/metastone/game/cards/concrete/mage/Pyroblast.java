package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Pyroblast extends SpellCard {

	public Pyroblast() {
		super("Pyroblast", Rarity.EPIC, HeroClass.MAGE, 10);
		setDescription("Deal $10 damage.");
		setSpell(DamageSpell.create(10));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 71;
	}
}
