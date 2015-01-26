package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class PutRandomMinionOnBoardSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		return create(targetPlayer, null);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, Race race) {
		SpellDesc desc = new SpellDesc(PutRandomMinionOnBoardSpell.class);
		desc.setTargetPlayer(targetPlayer);
		if (race != null) {
			desc.set(SpellArg.RACE, race);
		}
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		Race race = (Race) desc.get(SpellArg.RACE);
		EntityReference source = desc.getSourceEntity();
		switch (desc.getTargetPlayer()) {
		case BOTH:
			putRandomMinionFromDeckOnBoard(context, player, race, source);
			putRandomMinionFromDeckOnBoard(context, opponent, race, source);
			break;
		case OPPONENT:
			putRandomMinionFromDeckOnBoard(context, opponent, race, source);
			break;
		case SELF:
			putRandomMinionFromDeckOnBoard(context, player, race, source);
			break;
		default:
			break;
		}
	}

	private void putRandomMinionFromDeckOnBoard(GameContext context, Player player, Race race, EntityReference source) {
		MinionCard minionCard = null;
		if (race == null) {
			minionCard = (MinionCard) player.getDeck().getRandomOfType(CardType.MINION);
		} else {
			minionCard = (MinionCard) SpellUtils.getRandomCard(player.getDeck(), card -> card.getTag(GameTag.RACE) == race);
		}

		if (minionCard == null) {
			return;
		}
		SpellDesc summonSpell = SummonSpell.create(minionCard);
		summonSpell.setSourceEntity(source);
		context.getLogic().castSpell(player.getId(), summonSpell);
		context.getLogic().removeCard(player.getId(), minionCard);
	}

}
