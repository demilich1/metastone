package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Moonfire extends SpellCard {

	public Moonfire() {
		super("Moonfire", Rarity.FREE, HeroClass.DRUID, 0);
		setDescription("Deal $1 damage.");
		setSpell(DamageSpell.create(1));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 14;
	}
}
