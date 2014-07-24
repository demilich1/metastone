package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class SetTagSpell extends Spell {

	private final GameTag tag;
	private final Integer value;
	private final boolean temporary;

	public SetTagSpell(GameTag tag) {
		this(tag, null);
	}

	public SetTagSpell(GameTag tag, Integer value) {
		this(tag, value, false);
	}

	public SetTagSpell(GameTag tag, Integer value, boolean temporary) {
		this.tag = tag;
		this.value = value;
		this.temporary = temporary;

	}

	public boolean isTemporary() {
		return temporary;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (value != null) {
			target.setTag(tag, value);
		} else {
			target.setTag(tag);
		}
		
		if (isTemporary()) {
			Spell revertSpell = new RemoveTagSpell(tag);
			revertSpell.setTarget(target.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(new TurnEndTrigger(), revertSpell, true);
			context.getLogic().addGameEventListener(player, removeTrigger, target);
		}
	}

}
