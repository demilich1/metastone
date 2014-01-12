package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FrostNova extends SpellCard {

	public FrostNova() {
		super("Frost Nova", Rarity.FREE, HeroClass.MAGE, 3);
		setSpell(new ApplyTagSpell(GameTag.FROZEN));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(TargetKey.ENEMY_CHARACTERS);
	}

}
