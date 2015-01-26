package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Dream extends SpellCard {

	public Dream() {
		super("Dream", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Return a minion to its owners's hand.");

		setSpell(ReturnMinionToHandSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 463;
	}
}
