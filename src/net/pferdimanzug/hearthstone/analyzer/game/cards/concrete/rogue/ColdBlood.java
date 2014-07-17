package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ColdBlood extends SpellCard {

	public ColdBlood() {
		super("Cold Blood", Rarity.COMMON, HeroClass.ROGUE, 1);
		setDescription("Give a minion +2 Attack. Combo: +4 Attack instead.");
		ComboSpell comboSpell = new ComboSpell(new BuffSpell(2, 0), new BuffSpell(4, 0));
		setSpell(comboSpell);
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 289;
	}
}
