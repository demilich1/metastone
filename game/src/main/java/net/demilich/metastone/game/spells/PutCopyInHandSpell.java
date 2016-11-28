package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class PutCopyInHandSpell extends Spell {

	public static SpellDesc create() {
		return create(1);
	}

	public static SpellDesc create(EntityReference target, int amount) {
		Map<SpellArg, Object> arguments = SpellDesc.build(PutCopyInHandSpell.class);
		arguments.put(SpellArg.HOW_MANY, amount);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int amount) {
		return create(null, amount);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion minion = (Minion) target;

		int amount = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 1);
		Card sourceCard = minion.getSourceCard();
		for (int i = 0; i < amount; i++) {
			context.getLogic().receiveCard(player.getId(), sourceCard.getCopy());
		}
	}

}