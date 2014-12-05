package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.GainManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class GallywixsCoin extends SpellCard {

	public GallywixsCoin() {
		super("Gallywix's Coin", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Gain 1 Mana Crystal this turn only. (Won't trigger Gallywix.)");

		setTargetRequirement(TargetSelection.NONE);
		setSpell(GainManaSpell.create(+1));
		setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.GALLYWIX_COIN);
		
		setCollectible(false);
	}

}
