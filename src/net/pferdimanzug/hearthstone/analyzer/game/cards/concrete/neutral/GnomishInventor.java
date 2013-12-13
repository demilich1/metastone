package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecryDrawCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class GnomishInventor extends MinionCard {

	public GnomishInventor() {
		super("Gnomish Inventor", Rarity.FREE, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion gnomishInventor = createMinion(2, 4);
		gnomishInventor.setTag(GameTag.BATTLECRY, new BattlecryDrawCard());
		return gnomishInventor;
	}

}
