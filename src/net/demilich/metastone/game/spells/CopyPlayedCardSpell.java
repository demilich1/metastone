package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class CopyPlayedCardSpell extends Spell {
	
	public static SpellDesc create() {
		return create(TargetPlayer.OPPONENT);
	}

	public static SpellDesc create(TargetPlayer targetPlayer) {
		Map<SpellArg, Object> arguments = SpellDesc.build(CopyPlayedCardSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.EVENT_TARGET);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card targetCard = (Card) target;
		context.getLogic().receiveCard(player.getId(), targetCard.clone());
	}
}
