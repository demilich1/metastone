package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class TransformMinionSpell implements ISpell {
	
	private final MinionCard newMinion;

	public TransformMinionSpell(MinionCard newMinion) {
		this.newMinion = newMinion;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		int index = player.getMinions().indexOf(target);
		List<Minion> minions = index == -1 ? opponent.getMinions() : player.getMinions();
		if (index == -1) {
			index = opponent.getMinions().indexOf(target);
		}
		
		minions.remove(index);
		minions.add(index, newMinion.summon());
	}
	
	

}
