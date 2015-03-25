package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class WorgenInfiltrator extends MinionCard {

	public WorgenInfiltrator() {
		super("Worgen Infiltrator", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Stealth");
	}

	@Override
	public int getTypeId() {
		return 229;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTH);
	}
}
