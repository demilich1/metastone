package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
