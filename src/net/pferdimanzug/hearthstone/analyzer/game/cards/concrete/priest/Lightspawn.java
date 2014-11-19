package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Lightspawn extends MinionCard {

	public Lightspawn() {
		super("Lightspawn", 0, 5, Rarity.COMMON, HeroClass.PRIEST, 4);
		setDescription("This minion's Attack is always equal to its Health.");
	}

	@Override
	public int getTypeId() {
		return 267;
	}

	@Override
	public Minion summon() {
		Minion lightspawn = createMinion(GameTag.ATTACK_EQUALS_HP); 
		return lightspawn;
	}
}
