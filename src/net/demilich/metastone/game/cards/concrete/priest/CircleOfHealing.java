package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CircleOfHealing extends SpellCard {

	public CircleOfHealing() {
		super("Circle of Healing", Rarity.COMMON, HeroClass.PRIEST, 0);
		setDescription("Restore #4 Health to ALL minions.");
		setSpell(HealingSpell.create(4));
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}
	


	@Override
	public int getTypeId() {
		return 261;
	}
}
