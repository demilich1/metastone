package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class StormwindChampion extends MinionCard {

	public StormwindChampion() {
		super("Stormwind Champion", 6, 6, Rarity.FREE, HeroClass.ANY, 7);
		setDescription("Your other minions have +1/+1.");
	}

	@Override
	public Minion summon() {
		Minion stormwindChampion = createMinion();
		//TODO: add aura
		return stormwindChampion;
	}

}
