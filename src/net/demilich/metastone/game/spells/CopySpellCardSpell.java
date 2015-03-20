package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CopySpellCardSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(CopySpellCardSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.EVENT_TARGET);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card targetCard = (Card) target;
		Player owner = context.getPlayer(targetCard.getOwner());
		Player opponent = context.getOpponent(owner);
		Card copy = targetCard.clone();
		context.getLogic().receiveCard(opponent.getId(), copy);
	}
}
