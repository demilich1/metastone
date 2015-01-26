package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.GainManaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TheCoin extends SpellCard {
	
	public static final int MANA_GAIN = +1;

	public TheCoin() {
		super("The Coin", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Gain 1 Mana Crystal this turn only.");
		setCollectible(false);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(GainManaSpell.create(MANA_GAIN));
	}

	@Override
	public int getTypeId() {
		return 217;
	}
}
