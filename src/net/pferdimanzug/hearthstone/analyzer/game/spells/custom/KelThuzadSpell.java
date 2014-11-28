package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class KelThuzadSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(KelThuzadSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int currentTurn = context.getTurn();
		for (Minion deadMinion : player.getGraveyard()) {
			if (deadMinion.getTagValue(GameTag.DIED_ON_TURN) == currentTurn) {
				MinionCard minionCard = (MinionCard) deadMinion.getSourceCard();
				context.getLogic().summon(player.getId(), minionCard.summon());
			}
		}
	}
	
}