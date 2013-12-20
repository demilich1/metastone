package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaFreezeSpell;

public class FrostNova extends SpellCard {

	public FrostNova() {
		super("Frost Nova", Rarity.FREE, HeroClass.MAGE, 3);
		setSpell(new AreaFreezeSpell(0));
		setTargetRequirement(TargetSelection.NONE);
	}

}
