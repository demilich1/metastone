package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.ToggleCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.CardPlayedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;

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
		ToggleCostModifier costModifier = new ToggleCostModifier(CardType.MINION, -1, false);
		costModifier.setToggleOnTrigger(new TurnStartTrigger());
		costModifier.setToggleOffTrigger(new CardPlayedTrigger(TargetPlayer.SELF, CardType.MINION));
		pintSizedSummoner.setCardCostModifier(costModifier);
		return pintSizedSummoner;
	}

}
