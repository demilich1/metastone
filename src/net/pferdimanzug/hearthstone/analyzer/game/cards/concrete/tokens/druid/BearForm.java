package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class BearForm extends MinionCard {

	public BearForm() {
		super("Druid of the Claw (Bear)", 4, 6, Rarity.COMMON, HeroClass.DRUID, 5);
		
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

	@Override
	public int getTypeId() {
		return 415;
	}
}
