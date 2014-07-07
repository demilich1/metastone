package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class DamageSpell extends Spell {
	
	private int damage;
	private final IValueProvider damageModifier;
	
	public DamageSpell(int damage) {
		this(null);
		setDamage(damage);
	}
	
	public DamageSpell(IValueProvider damageModifier) {
		this.damageModifier = damageModifier;
	}
	
	public int getDamage() {
		return damage;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		int effectiveDamage = damageModifier != null ? damageModifier.provideValue(context, player, target) : getDamage();
		context.getLogic().damage(player, (Actor)target, effectiveDamage, applySpellpower);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
