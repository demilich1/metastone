package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArcaneShot extends SpellCard {

	public ArcaneShot() {
		super("Arcane Shot", Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Deal $2 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(2));
	}

	@Override
	public int getTypeId() {
		return 27;
	}
}
