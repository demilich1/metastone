package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class AnnoyOTron extends MinionCard {

	public AnnoyOTron() {
		super("Annoy-o-Tron", 1, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Taunt. Divine Shield");
		
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT, GameTag.DIVINE_SHIELD);
	}

}
