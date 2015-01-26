package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.GainManaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class GallywixsCoin extends SpellCard {

	public GallywixsCoin() {
		super("Gallywix's Coin", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("Gain 1 Mana Crystal this turn only. (Won't trigger Gallywix.)");

		setTargetRequirement(TargetSelection.NONE);
		setSpell(GainManaSpell.create(+1));
		setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.GALLYWIX_COIN);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 593;
	}
}
