package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class ShieldedMinibot extends MinionCard {

	public ShieldedMinibot() {
		super("Shielded Minibot", 2, 2, Rarity.COMMON, HeroClass.PALADIN, 2);
		setDescription("Divine Shield");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 558;
	}



	@Override
	public Minion summon() {
		Minion shieldedMinibot = createMinion(GameTag.DIVINE_SHIELD);
		return shieldedMinibot;
	}
}
