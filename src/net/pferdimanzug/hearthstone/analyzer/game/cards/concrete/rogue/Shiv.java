package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shiv extends SpellCard {

	public Shiv() {
		super("Shiv", Rarity.FREE, HeroClass.ROGUE, 2);
		setDescription("Deal $1 damage. Draw a card.");
		setSpell(new MetaSpell(new DamageSpell(1), new DrawCardSpell()));
		setTargetRequirement(TargetSelection.ANY);
	}

}
