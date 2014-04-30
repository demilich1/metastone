package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class SetHpSpell extends Spell {
	
	private final int hp;

	public SetHpSpell(int hp) {
		this.hp = hp;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		target.setHp(hp);
		target.setMaxHp(hp);
		target.removeTag(GameTag.HP_BONUS);
	}

}
