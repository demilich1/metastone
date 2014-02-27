package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComboSpell extends Spell {
	
	private static Logger logger = LoggerFactory.getLogger(ComboSpell.class);

	private final Spell noCombo;
	private final Spell combo;

	public ComboSpell(Spell noCombo, Spell combo) {
		this.noCombo = noCombo;
		this.combo = combo;
	}

	@Override
	public void cast(GameContext context, Player player, List<Actor> targets) {
		if (player.getHero().hasTag(GameTag.COMBO)) {
			logger.debug(GameTag.COMBO + " spell activated");
			combo.cast(context, player, targets);
		} else if (noCombo != null) {
			logger.debug("Played a card with " + GameTag.COMBO + " but no card was played before");
			noCombo.cast(context, player, targets);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
	}

	@Override
	public void setApplySpellpower(boolean applySpellpower) {
		super.setApplySpellpower(applySpellpower);
		if (noCombo != null) {
			noCombo.setApplySpellpower(applySpellpower);
		}
		if (combo != null) {
			combo.setApplySpellpower(applySpellpower);
		}
	}
	
	

}
