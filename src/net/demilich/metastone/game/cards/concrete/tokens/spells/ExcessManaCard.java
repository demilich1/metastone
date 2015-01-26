package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ExcessManaCard extends SpellCard {

	public ExcessManaCard() {
		super("Excess Mana", Rarity.FREE, HeroClass.DRUID, 0);
		setDescription("Draw a card. (You can only have 10 Mana in your tray.)");

		setSpell(DrawCardSpell.create(1));
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 464;
	}
}
