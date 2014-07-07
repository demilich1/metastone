package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformMinionSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);

	private final MinionCard templateCard;
	private final Minion transformTarget;

	public TransformMinionSpell(MinionCard templateCard) {
		this.templateCard = templateCard;
		this.transformTarget = null;
	}
	
	public TransformMinionSpell(Minion transformTarget) {
		this.templateCard = null;
		this.transformTarget = transformTarget;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		/*
		 * Player opponent = context.getOpponent(player); int index =
		 * player.getMinions().indexOf(target); List<Minion> minions = index ==
		 * -1 ? opponent.getMinions() : player.getMinions(); if (index == -1) {
		 * index = opponent.getMinions().indexOf(target); } minions.add(index,
		 * newMinion.summon());
		 */

		Minion minion = (Minion) target;
		Minion newMinion = transformTarget != null ? transformTarget : templateCard.summon();
		logger.debug("{} is transformed into a {}", minion, newMinion);
		context.getLogic().removeMinion(minion);
		context.getLogic().summon(minion.getOwner(), newMinion, null, null, false);

	}

}
