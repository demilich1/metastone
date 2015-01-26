package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.IAmMurlocSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class IAmMurloc extends SpellCard {

	public IAmMurloc() {
		super("I Am Murloc", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Summon three, four, or five 1/1 Murlocs.");

		setSpell(IAmMurlocSpell.create());
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 465;
	}
}
