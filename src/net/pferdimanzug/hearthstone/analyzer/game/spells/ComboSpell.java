package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ComboSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(ComboSpell.class);

	private Spell noCombo;
	private Spell combo;

	public ComboSpell(Spell noCombo, Spell combo) {
		this.noCombo = noCombo;
		this.combo = combo;
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (player.getHero().hasTag(GameTag.COMBO)) {
			logger.debug(GameTag.COMBO + " spell activated");
			combo.cast(context, player, targets);
		} else {
			logger.debug("Played a card with " + GameTag.COMBO + " but no card was played before");
			noCombo.cast(context, player, targets);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
	}

}
