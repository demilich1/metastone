package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class PutCopyInHandSpell extends Spell {

	public static SpellDesc create() {
		return create(1);
	}

	public static SpellDesc create(int amount) {
		SpellDesc desc = new SpellDesc(PutCopyInHandSpell.class);
		desc.setValue(amount);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;

		int amount = desc.getValue();
		Card sourceCard = minion.getSourceCard();
		for (int i = 0; i < amount; i++) {
			context.getLogic().receiveCard(player.getId(), sourceCard.clone());
		}
	}

}