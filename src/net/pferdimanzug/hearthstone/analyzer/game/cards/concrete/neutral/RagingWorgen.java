package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.EnrageWindfury;

public class RagingWorgen extends MinionCard {

	public RagingWorgen() {
		super("Raging Worgen", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Enrage: Windfury and +1 Attack");
	}

	@Override
	public Minion summon() {
		Minion ragingWorgen = createMinion();
		ragingWorgen.setTag(GameTag.ENRAGE_SPELL, new EnrageWindfury(1));
		return ragingWorgen;
	}

}
