package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class MindBlast extends SpellCard {

	public MindBlast() {
		super("Mind Blast", Rarity.FREE, HeroClass.PRIEST, 2);
		setSpell(new SingleTargetDamageSpell(5));
		setTargetRequirement(TargetSelection.ENEMY_HERO);
	}

}
