package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DamageSpell extends Spell {
	
	private int damage;
	
	public DamageSpell(int damage) {
		this.setDamage(damage);
	}
	
	public int getDamage() {
		return damage;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().damage(player, (Actor)target, getDamage(), applySpellpower);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
