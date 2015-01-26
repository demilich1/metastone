package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shiv extends SpellCard {

	public Shiv() {
		super("Shiv", Rarity.FREE, HeroClass.ROGUE, 2);
		setDescription("Deal $1 damage. Draw a card.");
		setSpell(MetaSpell.create(DamageSpell.create(1), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 304;
	}
}
