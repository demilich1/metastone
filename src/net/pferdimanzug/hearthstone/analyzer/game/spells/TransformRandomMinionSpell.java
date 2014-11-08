package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class TransformRandomMinionSpell extends TransformMinionSpell {

	public static SpellDesc create(MinionCard templateCard) {
		SpellDesc desc = new SpellDesc(TransformRandomMinionSpell.class);
		desc.set(SpellArg.CARD, templateCard);
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets.isEmpty()) {
			return;
		}
		Entity randomTarget = SpellUtils.getRandomTarget(targets);
		super.onCast(context, player, desc, randomTarget);
	}

}
