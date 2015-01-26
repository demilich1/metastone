package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArmorPlating extends SpellCard {

	public ArmorPlating() {
		super("Armor Plating", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a minion +1 Health.");
		
		setSpell(BuffSpell.create(0, 1));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 583;
	}
}
