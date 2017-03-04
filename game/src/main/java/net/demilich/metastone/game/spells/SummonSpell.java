package net.demilich.metastone.game.spells;

import java.util.Map;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SummonSpell extends Spell {

	public static SpellDesc create(MinionCard... minionCards) {
		return create(TargetPlayer.SELF, minionCards);
	}

	public static SpellDesc create(RelativeToSource relativeBoardPosition, MinionCard... minionCards) {
		return create(TargetPlayer.SELF, relativeBoardPosition, minionCards);
	}

	public static SpellDesc create(String minionCard) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonSpell.class);
		arguments.put(SpellArg.CARD, minionCard);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(String[] minionCards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonSpell.class);
		arguments.put(SpellArg.CARDS, minionCards);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, MinionCard... minionCards) {
		return create(targetPlayer, null, minionCards);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, RelativeToSource relativeBoardPosition, MinionCard... minionCards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonSpell.class);
		String[] cardNames = new String[minionCards.length];
		for (int i = 0; i < minionCards.length; i++) {
			cardNames[i] = minionCards[i].getCardId();
		}
		arguments.put(SpellArg.CARDS, cardNames);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		if (relativeBoardPosition != null) {
			arguments.put(SpellArg.BOARD_POSITION_RELATIVE, relativeBoardPosition);
		}
		return new SpellDesc(arguments);
	}

	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		int count = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		for (Card card : SpellUtils.getCards(context, desc)) {
			for (int i = 0; i < count; i++) {
				MinionCard minionCard = count == 1 ? (MinionCard) card : (MinionCard) card.clone();
				context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
			}
		}
	}

}
