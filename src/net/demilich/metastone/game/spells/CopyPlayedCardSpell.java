package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CopyPlayedCardSpell extends Spell {
	
	public static SpellDesc create() {
		return create(TargetPlayer.OPPONENT);
	}

	public static SpellDesc create(TargetPlayer targetPlayer) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CopyPlayedCardSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.EVENT_TARGET);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card targetCard = (Card) target;
		Player owner = context.getPlayer(targetCard.getOwner());
		Player opponent = context.getOpponent(owner);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		switch (targetPlayer) {
		case BOTH:
			context.getLogic().receiveCard(owner.getId(), targetCard.clone());
			context.getLogic().receiveCard(opponent.getId(), targetCard.clone());
			break;
		case OPPONENT:
			context.getLogic().receiveCard(opponent.getId(), targetCard.clone());
			break;
		case SELF:
			context.getLogic().receiveCard(owner.getId(), targetCard.clone());
			break;
		default:
			break;
		}
	}
}
