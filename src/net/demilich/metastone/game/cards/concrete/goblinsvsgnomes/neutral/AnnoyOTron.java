package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class AnnoyOTron extends MinionCard {

	public AnnoyOTron() {
		super("Annoy-o-Tron", 1, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Taunt. Divine Shield");
		
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 500;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT, GameTag.DIVINE_SHIELD);
	}
}
