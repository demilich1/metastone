package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SinisterStrike extends SpellCard {

	public SinisterStrike() {
		super("Sinister Strike", Rarity.FREE, HeroClass.ROGUE, 1);
		setDescription("Deal $3 damage to the enemy hero.");
		setSpell(DamageSpell.create(3));
		setTargetRequirement(TargetSelection.ENEMY_HERO);
	}



	@Override
	public int getTypeId() {
		return 306;
	}
}
