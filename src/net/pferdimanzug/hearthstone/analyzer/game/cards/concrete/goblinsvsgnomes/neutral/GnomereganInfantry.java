package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class GnomereganInfantry extends MinionCard {

	public GnomereganInfantry() {
		super("Gnomeregan Infantry", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Charge. Taunt");
	}

	@Override
	public Minion summon() {
		Minion gnomereganInfantry = createMinion(GameTag.CHARGE, GameTag.TAUNT);
		return gnomereganInfantry;
	}

}
