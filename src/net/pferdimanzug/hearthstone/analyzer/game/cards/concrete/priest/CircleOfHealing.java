package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CircleOfHealing extends SpellCard {

	public CircleOfHealing() {
		super("Circle of Healing", Rarity.COMMON, HeroClass.PRIEST, 0);
		setDescription("Restore #4 Health to ALL minions.");
		setSpell(new HealingSpell(4));
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}
	


	@Override
	public int getTypeId() {
		return 261;
	}
}
