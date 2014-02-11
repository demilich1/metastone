package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PowerWordShield extends SpellCard {

	public PowerWordShield() {
		super("Power Word: Shield", Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Draw a card.");
		setSpell(new MetaSpell(new BuffSpell(0, 2), new DrawCardSpell()));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
