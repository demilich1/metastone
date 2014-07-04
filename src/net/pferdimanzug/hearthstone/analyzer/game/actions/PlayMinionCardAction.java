package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PlayMinionCardAction extends PlayCardAction {

	private final Battlecry battlecry;

	public PlayMinionCardAction(Card card) {
		this(card, null);
	}

	public PlayMinionCardAction(Card card, Battlecry battlecry) {
		super(card);
		this.battlecry = battlecry;
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		setActionType(ActionType.SUMMON);
	}

	@Override
	protected void play(GameContext context, int playerId) {
		MinionCard minionCard = (MinionCard) getCard();
		Actor nextTo = (Actor) (getTargetKey() != null ? context.resolveSingleTarget(playerId, getTargetKey()) : null);
		Minion minion = minionCard.summon();
		if (battlecry != null) {
			minion.setBattlecry(battlecry);
		}
		context.getLogic().summon(playerId, minion, minionCard, nextTo, true);
	}

}
