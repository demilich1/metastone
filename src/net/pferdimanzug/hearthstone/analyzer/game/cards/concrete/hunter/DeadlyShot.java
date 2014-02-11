package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DeadlyShot extends SpellCard {

	public DeadlyShot() {
		super("Deadly Shot", Rarity.COMMON, HeroClass.HUNTER, 3);
		setDescription("Destroy a random enemy minion.");
		setSpell(new DestroyRandomSpell());
		setPredefinedTarget(EntityReference.ENEMY_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}

}
