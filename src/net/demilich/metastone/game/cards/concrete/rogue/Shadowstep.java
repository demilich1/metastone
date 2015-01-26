package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shadowstep extends SpellCard {

	public Shadowstep() {
		super("Shadowstep", Rarity.COMMON, HeroClass.ROGUE, 0);
		setDescription("Return a friendly minion to your hand. It costs (2) less.");
		setSpell(ReturnMinionToHandSpell.create(-2));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 303;
	}
}
