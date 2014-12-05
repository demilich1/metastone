package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Darkbomb extends SpellCard {

	public Darkbomb() {
		super("Darkbomb", Rarity.COMMON, HeroClass.WARLOCK, 2);
		setDescription("Deal $3 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(3));
	}

}
