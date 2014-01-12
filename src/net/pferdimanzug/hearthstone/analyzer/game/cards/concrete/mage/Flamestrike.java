package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Flamestrike extends SpellCard {

	public Flamestrike() {
		super("Flamestrike", Rarity.FREE, HeroClass.MAGE, 7);
		setSpell(new DamageSpell(4));
		setPredefinedTarget(TargetKey.ENEMY_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}


}
