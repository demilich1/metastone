package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.SummonCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SummonSpell extends Spell {

	public static SpellDesc create(SummonCard... summonCards) {
		return create(TargetPlayer.SELF, summonCards);
	}

	public static SpellDesc create(RelativeToSource relativeBoardPosition, SummonCard... summonCards) {
		return create(TargetPlayer.SELF, relativeBoardPosition, summonCards);
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

	public static SpellDesc create(TargetPlayer targetPlayer, SummonCard... summonCards) {
		return create(targetPlayer, null, summonCards);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, RelativeToSource relativeBoardPosition, SummonCard... summonCards) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonSpell.class);
		String[] cardNames = new String[summonCards.length];
		for (int i = 0; i < summonCards.length; i++) {
			cardNames[i] = summonCards[i].getCardId();
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
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		int count = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
		for (Card card : SpellUtils.getCards(context, desc)) {
			for (int i = 0; i < count; i++) {
				SummonCard summonCard = (SummonCard) card.clone();
				context.getLogic().summon(player.getId(), summonCard.summon(), null, boardPosition, false);
			}
		}
	}

}
