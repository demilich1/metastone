package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SenjinShieldmasta extends MinionCard {

	public SenjinShieldmasta() {
		super("Sen'jin Shieldmasta", 3, 5, Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 195;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
