package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Flamestrike extends SpellCard {

	public Flamestrike() {
		super("Flamestrike", Rarity.FREE, HeroClass.MAGE, 7);
		setDescription("Deal $4 damage to all enemy minions.");
		setSpell(DamageSpell.create(EntityReference.ENEMY_MINIONS, 4));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 60;
	}
}
