package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Whirlwind extends SpellCard {

	public Whirlwind() {
		super("Whirlwind", Rarity.FREE, HeroClass.WARRIOR, 1);
		setDescription("Deal $1 damage to ALL minions.");
		setSpell(DamageSpell.create(EntityReference.ALL_MINIONS, 1));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 383;
	}
}
