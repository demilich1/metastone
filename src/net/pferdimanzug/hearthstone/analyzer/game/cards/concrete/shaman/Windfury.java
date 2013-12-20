package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class Windfury extends SpellCard {

	public Windfury() {
		super("Windfury", Rarity.FREE, HeroClass.SHAMAN, 2);
		setSpell(new BuffSpell(GameTag.WINDFURY));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
