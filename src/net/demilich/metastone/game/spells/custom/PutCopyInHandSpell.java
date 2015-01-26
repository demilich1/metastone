package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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