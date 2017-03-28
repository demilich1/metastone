package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ReturnMinionToHandSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	public static SpellDesc create() {
		return create(null, null, false);
	}

	public static SpellDesc create(EntityReference target, SpellDesc spell, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ReturnMinionToHandSpell.class);
		arguments.put(SpellArg.SPELL, spell);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		SpellDesc cardSpell = (SpellDesc) desc.get(SpellArg.SPELL);
		Summon summon = (Summon) target;
		Player owner = context.getPlayer(summon.getOwner());
		if (owner.getHand().getCount() >= GameLogic.MAX_HAND_CARDS) {
			logger.debug("{} is destroyed because {}'s hand is full", summon, owner.getName());
			context.getLogic().markAsDestroyed((Actor) target);
		} else {
			logger.debug("{} is returned to {}'s hand", summon, owner.getName());
			context.getLogic().removeSummon(summon, true);
			Card sourceCard = summon.getSourceCard().getCopy();
			context.getLogic().receiveCard(summon.getOwner(), sourceCard);
			if (cardSpell != null) {
				context.setEventCard(sourceCard);
				SpellUtils.castChildSpell(context, player, cardSpell, source, sourceCard);
				context.setEventCard(null);
			}
		}
	}

}
