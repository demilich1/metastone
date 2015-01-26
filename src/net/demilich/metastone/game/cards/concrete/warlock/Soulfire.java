package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DiscardCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Soulfire extends SpellCard {

	public Soulfire() {
		super("Soulfire", Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Deal $4 damage. Discard a random card.");
		setSpell(MetaSpell.create(DamageSpell.create(4), DiscardCardSpell.create()));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 353;
	}
}
