package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BlessingOfKings extends SpellCard {

	public BlessingOfKings() {
		super("Blessing of Kings", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Give a minion +4/+4. (+4 Attack/+4 Health)");
		setSpell(BuffSpell.create(4, 4));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 238;
	}
}
