package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.UnleashTheHoundsSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class UnleashTheHounds extends SpellCard {

	public UnleashTheHounds() {
		super("Unleash the Hounds", Rarity.COMMON, HeroClass.HUNTER, 3);
		setDescription("For each enemy minion, summon a 1/1 Hound with Charge.");
		setSpell(UnleashTheHoundsSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 50;
	}
	
}
