package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformMinionSpell extends Spell {

	public static SpellDesc create(Minion transformTarget) {
		SpellDesc desc = new SpellDesc(TransformMinionSpell.class);
		desc.set(SpellArg.ENTITY, transformTarget);
		return desc;
	}
	
	public static SpellDesc create(MinionCard templateCard) {
		SpellDesc desc = new SpellDesc(TransformMinionSpell.class);
		desc.set(SpellArg.CARD, templateCard);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		Minion transformTarget = (Minion) desc.get(SpellArg.ENTITY);
		MinionCard templateCard = (MinionCard) desc.get(SpellArg.CARD);
		if (templateCard != null) {
			templateCard = (MinionCard) templateCard.clone();	
		}
		
		Minion newMinion = transformTarget != null ? transformTarget : templateCard.summon();
		int boardPosition = context.getBoardPosition(minion);
		logger.debug("{} is transformed into a {}", minion, newMinion);
		context.getLogic().removeMinion(minion);
		context.getLogic().summon(minion.getOwner(), newMinion, null, boardPosition, false);
	}

}
