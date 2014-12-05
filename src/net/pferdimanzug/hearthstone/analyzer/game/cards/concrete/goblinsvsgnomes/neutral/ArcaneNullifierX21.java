package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class ArcaneNullifierX21 extends MinionCard {

	public ArcaneNullifierX21() {
		super("Arcane Nullifier X-21", 2, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Taunt. Can't be targeted by spells or Hero Powers.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 502;
	}



	@Override
	public Minion summon() {
		Minion arcaneNullifierX21 = createMinion(GameTag.TAUNT, GameTag.UNTARGETABLE_BY_SPELLS);
		return arcaneNullifierX21;
	}
}
