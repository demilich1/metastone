package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SummoningPortal extends MinionCard {

	public SummoningPortal() {
		super("Summoning Portal", 0, 4, Rarity.COMMON, HeroClass.WARLOCK, 4);
		setDescription("Your minions cost (2) less, but not less than (1).");
	}

	@Override
	public int getTypeId() {
		return 355;
	}



	@Override
	public Minion summon() {
		Minion summoningPortal = createMinion();
		summoningPortal.setTag(GameTag.MINION_MANA_COST, -2);
		summoningPortal.setTag(GameTag.MINION_MIN_MANA_COST, 1);
		return summoningPortal;
	}
}
