package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class TransformMinionSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);

	public static SpellDesc create(EntityReference target, Minion transformTarget, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformMinionSpell.class);
		arguments.put(SpellArg.ENTITY, transformTarget);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(EntityReference target, String templateCard, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformMinionSpell.class);
		arguments.put(SpellArg.CARD, templateCard);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}
	
	public static SpellDesc create(Minion transformTarget) {
		return create(null, transformTarget, false);
	}

	public static SpellDesc create(String templateCard) {
		return create(null, templateCard, false);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion minion = (Minion) target;
		Minion transformTarget = (Minion) desc.get(SpellArg.ENTITY);
		String cardName = (String) desc.get(SpellArg.CARD);
		MinionCard templateCard = cardName != null ? (MinionCard) CardCatalogue.getCardById(cardName) : null;
		
		Minion newMinion = transformTarget != null ? transformTarget : templateCard.summon();
		int boardPosition = context.getBoardPosition(minion);
		logger.debug("{} is transformed into a {}", minion, newMinion);
		context.getLogic().removeMinion(minion);
		context.getLogic().summon(minion.getOwner(), newMinion, null, boardPosition, false);
	}

}
