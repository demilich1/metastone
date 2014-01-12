package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Vanish extends SpellCard {

	public Vanish() {
		super("Vanish", Rarity.FREE, HeroClass.ROGUE, 6);
		setSpell(new ReturnMinionToHandSpell());
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(TargetKey.ENEMY_MINIONS);
	}

}
