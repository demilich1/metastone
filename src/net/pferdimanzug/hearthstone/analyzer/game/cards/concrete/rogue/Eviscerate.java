package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Eviscerate extends SpellCard {

	public Eviscerate() {
		super("Eviscerate", Rarity.COMMON, HeroClass.ROGUE, 2);
		setSpell(new ComboSpell(new DamageSpell(2), new DamageSpell(4)));
		setTargetRequirement(TargetSelection.ANY);
	}

}
