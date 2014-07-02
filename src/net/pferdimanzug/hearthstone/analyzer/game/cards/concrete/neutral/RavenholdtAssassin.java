package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class RavenholdtAssassin extends MinionCard {

	public RavenholdtAssassin() {
		super("Ravenholdt Assassin", 7, 5, Rarity.RARE, HeroClass.ANY, 7);
		setDescription("Stealth");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTHED);
	}

}
