package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PlayMinionCardAction extends PlayCardAction {

	private final BattlecryAction battlecry;

	public PlayMinionCardAction(CardReference cardReference) {
		this(cardReference, null);
	}

	public PlayMinionCardAction(CardReference cardReference, BattlecryAction battlecry) {
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
