package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.PermanentCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.minions.Permanent;
import net.demilich.metastone.game.targeting.CardReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PlayPermanentCardAction extends PlayCardAction {

	private final BattlecryAction battlecry;

	public PlayPermanentCardAction(CardReference cardReference) {
		this(cardReference, null);
	}

	public PlayPermanentCardAction(CardReference cardReference, BattlecryAction battlecry) {
		super(cardReference);
		this.battlecry = battlecry;
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		setActionType(ActionType.SUMMON);
	}

	@Override
	public String getPromptText() {
		return "[Summon permanent]";
	}

	@Override
	protected void play(GameContext context, int playerId) {
		PermanentCard permanentCard = (PermanentCard) context.getPendingCard();
		Actor nextTo = (Actor) (getTargetKey() != null ? context.resolveSingleTarget(getTargetKey()) : null);
		Permanent permanent = permanentCard.summon();
		if (battlecry != null) {
			permanent.setBattlecry(battlecry);
		}
		Player player = context.getPlayer(playerId);
		int index = player.getSummons().indexOf(nextTo);
		context.getLogic().summon(playerId, permanent, permanentCard, index, true);
	}

}
