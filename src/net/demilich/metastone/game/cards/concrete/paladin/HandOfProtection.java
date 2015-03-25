package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HandOfProtection extends SpellCard {

	public HandOfProtection() {
		super("Hand of Protection", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Give a minion Divine Shield.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(AddAttributeSpell.create(GameTag.DIVINE_SHIELD));
	}



	@Override
	public int getTypeId() {
		return 247;
	}
}
