package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.BrawlSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Brawl extends SpellCard {

	public Brawl() {
		super("Brawl", Rarity.EPIC, HeroClass.WARRIOR, 5);
		setDescription("Destroy all minions except one.  (chosen randomly)");

		//TODO: check if this spell can be played with 1 minion on the board
		setSpell(BrawlSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 363;
	}

	
}
