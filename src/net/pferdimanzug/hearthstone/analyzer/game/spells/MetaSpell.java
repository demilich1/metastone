package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class MetaSpell implements ISpell {
	
	private final ISpell spell1;
	private final ISpell spell2;

	public MetaSpell(ISpell spell1, ISpell spell2) {
		this.spell1 = spell1;
		this.spell2 = spell2;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		spell1.cast(context, player, target);
		spell2.cast(context, player, target);
	}

}
