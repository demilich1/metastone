package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
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
	public String getPromptText() {
		return "[Summon minion]";
	}

	@Override
	protected void play(GameContext context, int playerId) {
		MinionCard minionCard = (MinionCard) context.getEnvironment().get(Environment.PENDING_CARD);
		Actor nextTo = (Actor) (getTargetKey() != null ? context.resolveSingleTarget(getTargetKey()) : null);
		Minion minion = minionCard.summon();
		if (battlecry != null) {
			minion.setBattlecry(battlecry);
		}
		Player player = context.getPlayer(playerId);
		int index = player.getMinions().indexOf(nextTo);
		context.getLogic().summon(playerId, minion, minionCard, index, true);
	}

}
