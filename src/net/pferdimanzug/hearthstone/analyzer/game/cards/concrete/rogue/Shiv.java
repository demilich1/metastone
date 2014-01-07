package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageAndDrawCardSpell;

public class Shiv extends SpellCard {

	public Shiv() {
		super("Shiv", Rarity.FREE, HeroClass.ROGUE, 2);
		setSpell(new DamageAndDrawCardSpell(1));
		setTargetRequirement(TargetSelection.ANY);
	}

}
