package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class KirinTorMage extends MinionCard {

	public KirinTorMage() {
		super("Kirin Tor Mage", 4, 3, Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Battlecry: The next Secret you play this turn costs (0).");
	}

	@Override
	public int getTypeId() {
		return 66;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.ONE_TIME_FREE_SECRET);
	}
}
