package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class AddDeathrattleSpell extends Spell {
	
	private final Spell deathrattleSpell;

	public AddDeathrattleSpell(Spell deathrattleSpell) {
		this.deathrattleSpell = deathrattleSpell;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion minion = (Minion) target;
		minion.addDeathrattle(deathrattleSpell);
	}

}
