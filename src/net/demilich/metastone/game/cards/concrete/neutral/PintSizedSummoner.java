package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.ToggleCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class PintSizedSummoner extends MinionCard {

	public PintSizedSummoner() {
		super("Pint-Sized Summoner", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("The first minion you play each turn costs (1) less.");
	}

	@Override
	public int getTypeId() {
		return 182;
	}

	@Override
	public Minion summon() {
		Minion pintSizedSummoner = createMinion();
		ToggleCostModifier costModifier = new ToggleCostModifier(CardType.MINION, -1);
		costModifier.setToggleOnTrigger(new TurnStartTrigger());
		costModifier.setToggleOffTrigger(new CardPlayedTrigger(TargetPlayer.SELF, CardType.MINION));
		pintSizedSummoner.setCardCostModifier(costModifier);
		return pintSizedSummoner;
	}

}
