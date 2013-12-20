package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DamageAndFreezeSpell extends SingleTargetDamageSpell {

	public DamageAndFreezeSpell(int damage) {
		super(damage);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		super.cast(context, player, target);
		target.setTag(GameTag.FROZEN);
	}

}
