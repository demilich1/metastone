package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.logic.CustomCloneable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformMinionSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);

	private final Minion transformTarget;

	public TransformMinionSpell(Minion transformTarget) {
		setCloneableData((CustomCloneable)null);
		this.transformTarget = transformTarget;
	}
	
	public TransformMinionSpell(MinionCard templateCard) {
		setCloneableData(templateCard);
		this.transformTarget = null;
	}

	public MinionCard getTemplateCard() {
		return (MinionCard) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		Minion newMinion = transformTarget != null ? transformTarget : getTemplateCard().summon();
		logger.debug("{} is transformed into a {}", minion, newMinion);
		context.getLogic().removeMinion(minion);
		context.getLogic().summon(minion.getOwner(), newMinion, null, null, false);

	}

}
