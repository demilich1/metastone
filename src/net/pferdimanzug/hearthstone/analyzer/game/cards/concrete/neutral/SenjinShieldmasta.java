package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
