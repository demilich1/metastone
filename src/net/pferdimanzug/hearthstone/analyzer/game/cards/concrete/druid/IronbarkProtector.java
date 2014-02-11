package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class IronbarkProtector extends MinionCard {

	public IronbarkProtector() {
		super("Ironbark Protector", 8, 8, Rarity.FREE, HeroClass.DRUID, 8);
		setDescription("Taunt");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}
