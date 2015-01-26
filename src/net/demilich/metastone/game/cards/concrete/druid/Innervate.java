package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.GainManaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Innervate extends SpellCard {

	public Innervate() {
		super("Innervate", Rarity.FREE, HeroClass.DRUID, 0);
		setDescription("Gain 2 Mana Crystals this turn only.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(GainManaSpell.create(2));
	}

	@Override
	public int getTypeId() {
		return 9;
	}
}
