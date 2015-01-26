package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class RoguesDoIt extends SpellCard {

	public RoguesDoIt() {
		super("Rogues Do it...", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Deal 4 damage. Draw a card.");

		setSpell(MetaSpell.create(DamageSpell.create(4), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.ANY);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 468;
	}
}
