package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class GilblinStalker extends MinionCard {

	public GilblinStalker() {
		super("Gilblin Stalker", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Stealth");
	}

	@Override
	public int getTypeId() {
		return 517;
	}



	@Override
	public Minion summon() {
		Minion gilblinStalker = createMinion(GameTag.STEALTHED);
		return gilblinStalker;
	}
}
