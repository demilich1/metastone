package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
