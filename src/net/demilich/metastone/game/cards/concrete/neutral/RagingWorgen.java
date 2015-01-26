package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.enrage.EnrageWindfury;

public class RagingWorgen extends MinionCard {

	public RagingWorgen() {
		super("Raging Worgen", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Enrage: Windfury and +1 Attack");
	}

	@Override
	public int getTypeId() {
		return 185;
	}

	@Override
	public Minion summon() {
		Minion ragingWorgen = createMinion();
		ragingWorgen.setTag(GameTag.ENRAGE_SPELL, EnrageWindfury.create(1));
		return ragingWorgen;
	}
}
