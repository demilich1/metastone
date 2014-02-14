package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class StonetuskBoar extends MinionCard {

	public StonetuskBoar() {
		super("Stonetusk Boar", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Charge");
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}

}
