package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class RaceSpecificBuffSpell extends BuffSpell {

	private Race race;

	public RaceSpecificBuffSpell(int attackBonus, int hpBonus, Race race) {
		super(attackBonus, hpBonus);
		this.race = race;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Minion minion = (Minion) target;
		if (minion.getRace() != race) {
			return;
		}
		
		super.onCast(context, player, target);
	}
	

}
