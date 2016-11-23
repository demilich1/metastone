package net.demilich.metastone.game.spells.desc.source;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.spells.TargetPlayer;

public abstract class CardSource {

	protected final SourceDesc desc;

	public CardSource(SourceDesc desc) {
		this.desc = desc;
	}
	
	public Object getArg(SourceArg arg) {
		return desc.get(arg);
	}
	
	public boolean hasArg(SourceArg arg) {
		return desc.contains(arg);
	}

	public CardCollection getCards(GameContext context, Player player) {
		TargetPlayer targetPlayer = (TargetPlayer) desc.get(SourceArg.TARGET_PLAYER);
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		Player providingPlayer = null;
		switch (targetPlayer) {
		case ACTIVE:
			providingPlayer = context.getActivePlayer();
			break;
		case BOTH:
			CardCollection cards = new CardCollection();
			for (Player selectedPlayer : context.getPlayers()) {
				cards.addAll(this.match(context, selectedPlayer));
			}
			return cards;
		case INACTIVE:
			providingPlayer = context.getOpponent(context.getActivePlayer());
			break;
		case OPPONENT:
			providingPlayer = context.getOpponent(player);
			break;
		case OWNER:
		case SELF:
		default:
			providingPlayer = player;
			break;
		}
		return this.match(context, providingPlayer);
	}

	protected abstract CardCollection match(GameContext context, Player player);

}
