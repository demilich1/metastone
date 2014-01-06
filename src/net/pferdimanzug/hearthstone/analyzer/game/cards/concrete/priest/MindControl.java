package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlSpell;

public class MindControl extends SpellCard {

	public MindControl() {
		super("Mind Control", Rarity.FREE, HeroClass.PRIEST, 10);
		setSpell(new MindControlSpell());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

}
