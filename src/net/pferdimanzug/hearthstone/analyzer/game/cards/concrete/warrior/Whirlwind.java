package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;

public class Whirlwind extends SpellCard {

	public Whirlwind() {
		super("Whirlwind", Rarity.FREE, HeroClass.WARRIOR, 1);
		setSpell(new AreaDamageSpell(1, TargetSelection.MINIONS));
		setTargetRequirement(TargetSelection.NONE);
	}

}
