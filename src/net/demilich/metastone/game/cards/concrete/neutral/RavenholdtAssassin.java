package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class RavenholdtAssassin extends MinionCard {

	public RavenholdtAssassin() {
		super("Ravenholdt Assassin", 7, 5, Rarity.RARE, HeroClass.ANY, 7);
		setDescription("Stealth");
	}

	@Override
	public int getTypeId() {
		return 188;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTHED);
	}
}
