package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BlessingOfKings extends SpellCard {

	public BlessingOfKings() {
		super("Blessing of Kings", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Give a minion +4/+4. (+4 Attack/+4 Health)");
		setSpell(new BuffSpell(4, 4));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 238;
	}
}
