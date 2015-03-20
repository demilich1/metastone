package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.BouncingBladeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BouncingBlade extends SpellCard {

	public BouncingBlade() {
		super("Bouncing Blade", Rarity.EPIC, HeroClass.WARRIOR, 3);
		setDescription("Deals $1 damage to a random minion. Repeat until a minion dies.");
		
		setSpell(BouncingBladeSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 603;
	}
}
