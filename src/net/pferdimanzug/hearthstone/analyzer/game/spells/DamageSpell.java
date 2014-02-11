package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class DamageSpell extends Spell {
	
	private final int damage;
	public DamageSpell(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		context.getLogic().damage(player, target, getDamage(), applySpellpower);
	}

}
