package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HealingTouch extends SpellCard {

	public HealingTouch() {
		super("Healing Touch", Rarity.FREE, HeroClass.DRUID, 3);
		setDescription("Restore #8 Health.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(HealingSpell.create(8));
	}

	@Override
	public int getTypeId() {
		return 8;
	}
}
