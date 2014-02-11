package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FrostNova extends SpellCard {

	public FrostNova() {
		super("Frost Nova", Rarity.FREE, HeroClass.MAGE, 3);
		setDescription("Freeze all enemy minions.");
		setSpell(new ApplyTagSpell(GameTag.FROZEN));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ENEMY_CHARACTERS);
	}

}
