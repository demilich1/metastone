package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class ShieldedMinibot extends MinionCard {

	public ShieldedMinibot() {
		super("Shielded Minibot", 2, 2, Rarity.COMMON, HeroClass.PALADIN, 2);
		setDescription("Divine Shield");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion shieldedMinibot = createMinion(GameTag.DIVINE_SHIELD);
		return shieldedMinibot;
	}

}
