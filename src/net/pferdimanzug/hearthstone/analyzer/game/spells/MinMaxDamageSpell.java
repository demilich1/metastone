package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class MinMaxDamageSpell extends DamageSpell {
	
	private final int minDamage;
	private final int maxDamage;

	public MinMaxDamageSpell(int minDamage, int maxDamage) {
		super(0);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		int damageRange = maxDamage - minDamage;
		int damageRoll = minDamage + ThreadLocalRandom.current().nextInt(damageRange + 1);
		setDamage(damageRoll);
		super.onCast(context, player, target);
	}

}
