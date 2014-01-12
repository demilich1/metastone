package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Whirlwind extends SpellCard {

	public Whirlwind() {
		super("Whirlwind", Rarity.FREE, HeroClass.WARRIOR, 1);
		setSpell(new DamageSpell(1));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(TargetKey.ALL_MINIONS);
	}

}
