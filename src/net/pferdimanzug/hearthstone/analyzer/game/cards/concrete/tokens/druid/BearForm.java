package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class BearForm extends MinionCard {

	public BearForm() {
		super("Druid of the Claw (Bear)", 4, 6, Rarity.COMMON, HeroClass.DRUID, 5);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 415;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
