package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BlessingOfMight extends SpellCard {

	public BlessingOfMight() {
		super("Blessing of Might", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Give a minion +3 Attack.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(BuffSpell.create(3, 0));
	}


	@Override
	public int getTypeId() {
		return 239;
	}
}
