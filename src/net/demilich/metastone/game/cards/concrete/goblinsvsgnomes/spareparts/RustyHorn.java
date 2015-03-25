package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class RustyHorn extends SpellCard {

	public RustyHorn() {
		super("Rusty Horn", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a minion Taunt.");
		
		setSpell(AddAttributeSpell.create(GameTag.TAUNT));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 587;
	}
}
