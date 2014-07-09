package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class TransformRandomMinionSpell extends TransformMinionSpell {

	public TransformRandomMinionSpell(MinionCard templateCard) {
		super(templateCard);
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		Entity randomTarget = getRandomTarget(targets);
		super.onCast(context, player, randomTarget);
	}

}
