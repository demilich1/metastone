package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PlayMinionCardAction extends PlayCardAction {

	private final Battlecry battlecry;

	public PlayMinionCardAction(CardReference cardReference) {
		this(cardReference, null);
	}

	public PlayMinionCardAction(CardReference cardReference, Battlecry battlecry) {
		super(cardReference);
		this.battlecry = battlecry;
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		setActionType(ActionType.SUMMON);
	}

	@Override
	protected void play(GameContext context, int playerId) {
		MinionCard minionCard = (MinionCard) context.resolveCardReference(getCardReference());
		if (minionCard == null) {
			System.out.println("MinionCard is NULL: " + getCardReference());
		}
		Actor nextTo = (Actor) (getTargetKey() != null ? context.resolveSingleTarget(getTargetKey()) : null);
		Minion minion = minionCard.summon();
		if (battlecry != null) {
			minion.setBattlecry(battlecry);
		}
		context.getLogic().summon(playerId, minion, minionCard, nextTo, true);
	}

}
