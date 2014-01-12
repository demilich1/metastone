package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Consecration extends SpellCard {

	public Consecration() {
		super("Consecration", Rarity.FREE, HeroClass.PALADIN, 4);
		setSpell(new DamageSpell(2));
		setPredefinedTarget(TargetKey.ENEMY_CHARACTERS);
		setTargetRequirement(TargetSelection.NONE);
	}

}
