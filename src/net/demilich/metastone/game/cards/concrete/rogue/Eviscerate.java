package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ComboSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Eviscerate extends SpellCard {

	public Eviscerate() {
		super("Eviscerate", Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Deal $2 damage. Combo: Deal $4 damage instead.");
		setSpell(ComboSpell.create(DamageSpell.create(2), DamageSpell.create(4)));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 294;
	}
}
