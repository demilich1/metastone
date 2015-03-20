package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Hellfire extends SpellCard {

	public Hellfire() {
		super("Hellfire", Rarity.FREE, HeroClass.WARLOCK, 4);
		setDescription("Deal $3 damage to ALL characters.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(DamageSpell.create(EntityReference.ALL_CHARACTERS, 3));
	}

	@Override
	public int getTypeId() {
		return 343;
	}
}
