package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class AddDeathrattleSpell extends Spell {

	public AddDeathrattleSpell(Spell deathrattleSpell) {
		setCloneableData(deathrattleSpell);
	}

	public Spell getDeathrattleSpell() {
		return (Spell) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Actor minion = (Actor) target;
		minion.addDeathrattle(getDeathrattleSpell());
	}

}
