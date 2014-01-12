package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Hellfire extends SpellCard {

	public Hellfire() {
		super("Hellfire", Rarity.FREE, HeroClass.WARLOCK, 4);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DamageSpell(3));
		setPredefinedTarget(TargetKey.ALL_CHARACTERS);
	}

}
