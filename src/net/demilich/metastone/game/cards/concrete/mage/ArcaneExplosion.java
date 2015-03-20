package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArcaneExplosion extends SpellCard {

	public ArcaneExplosion() {
		super("Arcane Explosion", Rarity.FREE, HeroClass.MAGE, 2);
		setDescription("Deal $1 damage to all enemy minions.");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(DamageSpell.create(EntityReference.ENEMY_MINIONS, 1));
	}

	@Override
	public int getTypeId() {
		return 51;
	}
}
