package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.HumilitySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Humility extends SpellCard {

	public Humility() {
		super("Humility", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Change a minion's Attack to 1.");
		setSpell(HumilitySpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 250;
	}
	
}
