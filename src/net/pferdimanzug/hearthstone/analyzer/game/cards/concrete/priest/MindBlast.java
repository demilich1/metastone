package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MindBlast extends SpellCard {

	public MindBlast() {
		super("Mind Blast", Rarity.FREE, HeroClass.PRIEST, 2);
		setSpell(new DamageSpell(5));
		setTargetRequirement(TargetSelection.ENEMY_HERO);
	}

}
