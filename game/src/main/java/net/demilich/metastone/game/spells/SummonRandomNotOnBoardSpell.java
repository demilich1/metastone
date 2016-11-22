package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SummonRandomNotOnBoardSpell extends Spell {

	private static boolean alreadyOnBoard(List<Minion> minions, String id) {
		for (Minion minion : minions) {
			if (minion.getSourceCard().getCardId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		String[] minionCardsId = (String[]) desc.get(SpellArg.CARDS);
		List<String> eligibleMinions = new ArrayList<String>();
		for (String minion : minionCardsId) {
			if (!alreadyOnBoard(player.getMinions(), minion)) {
				eligibleMinions.add(minion);
			}
		}
		if (eligibleMinions.isEmpty()) {
			return;
		}

		String randomMinionId = eligibleMinions.get(context.getLogic().random(eligibleMinions.size()));
		MinionCard randomMinionCard = (MinionCard) context.getCardById(randomMinionId);
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}
