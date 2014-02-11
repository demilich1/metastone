package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class VentureCoMercenary extends MinionCard {

	public VentureCoMercenary() {
		super("Venture Co. Mercenary", 7, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Your minions cost (3) more.");
	}

	@Override
	public Minion summon() {
		Minion ventureCoMercenary = createMinion();
		ventureCoMercenary.setTag(GameTag.MINION_MANA_COST, 3);
		return ventureCoMercenary;
	}

}
