package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ComboSpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(ComboSpell.class);

	private ISpell noCombo;
	private ISpell combo;

	public ComboSpell(ISpell noCombo, ISpell combo) {
		this.noCombo = noCombo;
		this.combo = combo;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		if (player.getHero().hasTag(GameTag.COMBO)) {
			logger.debug("Combo spell cast");
			context.getLogic().castSpell(player, combo, target);
		} else {
			context.getLogic().castSpell(player, noCombo, target);
		}
		
	}

}
