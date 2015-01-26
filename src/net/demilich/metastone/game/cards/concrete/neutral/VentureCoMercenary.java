package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.MinionCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class VentureCoMercenary extends MinionCard {

	public VentureCoMercenary() {
		super("Venture Co. Mercenary", 7, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Your minions cost (3) more.");
	}

	@Override
	public int getTypeId() {
		return 221;
	}

	@Override
	public Minion summon() {
		Minion ventureCoMercenary = createMinion();
		MinionCostModifier costModifier = new MinionCostModifier(3);
		ventureCoMercenary.setCardCostModifier(costModifier);
		return ventureCoMercenary;
	}
}
