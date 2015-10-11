package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ReturnMinionToHandSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	public static SpellDesc create() {
		return create(null, 0, false);
	}

	public static SpellDesc create(EntityReference target, int manaModifier, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ReturnMinionToHandSpell.class);
		arguments.put(SpellArg.MANA_MODIFIER, manaModifier);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int manaCostModifier = desc.getInt(SpellArg.MANA_MODIFIER, 0);
		Minion minion = (Minion) target;
		Player owner = context.getPlayer(minion.getOwner());
		if (owner.getHand().getCount() >= context.getLogic().MAX_HAND_CARDS) {
			logger.debug("{} is destroyed because {}'s hand is full", minion, owner.getName());
			context.getLogic().markAsDestroyed((Actor) target);
		} else {
			logger.debug("{} is returned to {}'s hand", minion, owner.getName());
			context.getLogic().removeMinion(minion);
			Card sourceCard = minion.getSourceCard().getCopy();
			context.getLogic().receiveCard(minion.getOwner(), sourceCard);
			sourceCard.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
		}
	}

}
