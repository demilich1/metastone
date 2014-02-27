package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class GainMaxManaSpell extends Spell {
	
	private final boolean opponent;

	public GainMaxManaSpell() {
		this(false);
	}
	
	public GainMaxManaSpell(boolean opponent) {
		this.opponent = opponent;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Player affectedPlayer = opponent ? context.getOpponent(player) : player;
		if (affectedPlayer.getMaxMana() < GameLogic.MAX_MANA) {
			affectedPlayer.setMaxMana(affectedPlayer.getMaxMana() + 1);
		} 
	}

}
