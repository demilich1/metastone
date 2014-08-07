package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.HumilitySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Humility extends SpellCard {

	public Humility() {
		super("Humility", Rarity.FREE, HeroClass.PALADIN, 1);
		setDescription("Change a minion's Attack to 1.");
		setSpell(HumilitySpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 250;
	}
	
}
