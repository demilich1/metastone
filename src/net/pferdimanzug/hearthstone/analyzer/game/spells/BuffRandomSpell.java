package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffRandomSpell extends BuffSpell {
	
	public BuffRandomSpell(int attackBonus, int hpBonus) {
		super(attackBonus, hpBonus);
	}

	public BuffRandomSpell(int attackBonus) {
		super(attackBonus);
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		Entity randomTarget = targets.get(ThreadLocalRandom.current().nextInt(targets.size()));
		onCast(context, player, randomTarget);
	}

}
