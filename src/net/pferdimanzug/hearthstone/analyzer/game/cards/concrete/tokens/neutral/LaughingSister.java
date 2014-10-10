package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class LaughingSister extends MinionCard {

	public LaughingSister() {
		super("Laughing Sister", 3, 5, Rarity.FREE, HeroClass.ANY, 3);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 444;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.UNTARGETABLE_BY_SPELLS);
	}
}
