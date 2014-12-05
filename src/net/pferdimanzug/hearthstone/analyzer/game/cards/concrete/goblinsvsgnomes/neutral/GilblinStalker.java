package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
