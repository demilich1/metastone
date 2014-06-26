package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SetHpSpell extends Spell {
	
	private final int hp;

	public SetHpSpell(int hp) {
		this.hp = hp;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Actor targetActor = (Actor) target;
		targetActor.modifyHpBonus(-(targetActor.getHp() - hp));
	}

}
