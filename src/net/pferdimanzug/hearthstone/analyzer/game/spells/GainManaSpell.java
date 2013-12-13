package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class GainManaSpell implements ISpell {

	private final int mana;

	public GainManaSpell(int mana) {
		this.mana = mana;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		context.getLogic().modifyCurrentMana(player, mana);
	}

}
