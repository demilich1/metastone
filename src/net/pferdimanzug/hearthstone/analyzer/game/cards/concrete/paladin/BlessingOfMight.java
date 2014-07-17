package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BlessingOfMight extends SpellCard {

	public BlessingOfMight() {
		super("Blessing of Might", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Give a minion +3 Attack.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(new BuffSpell(3, 0));
	}


	@Override
	public int getTypeId() {
		return 239;
	}
}
