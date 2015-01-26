package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Darkbomb extends SpellCard {

	public Darkbomb() {
		super("Darkbomb", Rarity.COMMON, HeroClass.WARLOCK, 2);
		setDescription("Deal $3 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(3));
	}



	@Override
	public int getTypeId() {
		return 596;
	}
}
