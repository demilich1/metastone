package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.GainManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TheCoin extends SpellCard {
	
	public static final int MANA_GAIN = +1;

	public TheCoin() {
		super("The Coin", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Gain 1 Mana Crystal this turn only.");
		setCollectible(false);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new GainManaSpell(MANA_GAIN));
	}

	@Override
	public int getTypeId() {
		return 217;
	}
}
