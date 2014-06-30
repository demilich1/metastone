package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SetAttackSpell extends Spell {
	private final int attack;

	public SetAttackSpell(int attack) {
		this.attack = attack;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Actor targetActor = (Actor) target;
		targetActor.modifyTag(GameTag.ATTACK_BONUS, -targetActor.getAttack() + attack);
	}
}
