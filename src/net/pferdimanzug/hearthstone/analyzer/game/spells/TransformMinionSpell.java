package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		templateCard = (MinionCard) templateCard.clone();
		Minion newMinion = transformTarget != null ? transformTarget : templateCard.summon();
		logger.debug("{} is transformed into a {}", minion, newMinion);
		context.getLogic().removeMinion(minion);
		context.getLogic().summon(minion.getOwner(), newMinion, null, null, false);
	}

}
