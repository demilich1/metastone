package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.targeting.TargetSelection;

public class GangUp extends SpellCard {

	public GangUp() {
		super("Gang Up", Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Choose a minion. Shuffle 3 copies of it into your deck.");
		
		//setSpell(ShuffleMinionToDeckSpell.create(null, null, 3));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 637;
	}
}
