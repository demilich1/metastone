package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;

public class Flamestrike extends SpellCard {

	public Flamestrike() {
		super("Flamestrike", Rarity.FREE, HeroClass.MAGE, 7);
		setSpell(new AreaDamageSpell(4, TargetSelection.ENEMY_MINIONS));
		setTargetRequirement(TargetSelection.NONE);
	}

}
