package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnMinionToHandSpell extends Spell {

	public static SpellDesc create() {
		return create(0);
	}
	
	public static SpellDesc create(int manaModifier) {
		SpellDesc desc = new SpellDesc(ReturnMinionToHandSpell.class);
		desc.set(SpellArg.MANA, manaModifier);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(ReturnMinionToHandSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int manaCostModifier = desc.getInt(SpellArg.MANA);
		Minion minion = (Minion) target;
		Player owner = context.getPlayer(minion.getOwner());
		logger.debug("{} is returned to {}'s hand", minion, owner.getName());
		context.getLogic().removeMinion(minion);
		Card sourceCard = minion.getSourceCard().clone();
		context.getLogic().receiveCard(minion.getOwner(), sourceCard);
		sourceCard.setTag(GameTag.MANA_COST_MODIFIER, manaCostModifier);
	}

}
