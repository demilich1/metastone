package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ReincarnateSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Reincarnate extends SpellCard {

	public Reincarnate() {
		super("Reincarnate", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Destroy a minion, then return it to life with full Health.");
		setSpell(ReincarnateSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 396;
	}



	
}
