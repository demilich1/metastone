package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ManaWraith extends MinionCard {

	public ManaWraith() {
		super("Mana Wraith", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("ALL minions cost (1) more.");
	}

	@Override
	public int getTypeId() {
		return 163;
	}



	@Override
	public Minion summon() {
		Minion manaWraith = createMinion();
		manaWraith.setTag(GameTag.ALL_MINION_MANA_COST, +1);
		return manaWraith;
	}
}
