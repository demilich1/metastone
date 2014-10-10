package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class CatForm extends MinionCard {

	public CatForm() {
		super("Druid of the Claw (Cat)", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 416;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
