package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class WhirlingZapOmatic extends MinionCard {

	public WhirlingZapOmatic() {
		super("Whirling Zap-o-matic", 3, 2, Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Windfury");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}

}
