package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.GainManaSpell;

public class TheCoin extends SpellCard {
	
	public static final int MANA_GAIN = +1;

	public TheCoin() {
		super("The Coin", Rarity.FREE, HeroClass.ANY, 0);
		setCollectible(false);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new GainManaSpell(MANA_GAIN));
	}

}
