package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;

public class Hellfire extends SpellCard {

	public Hellfire() {
		super("Hellfire", Rarity.FREE, HeroClass.WARLOCK, 4);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new AreaDamageSpell(3, TargetSelection.ANY));
	}

}
