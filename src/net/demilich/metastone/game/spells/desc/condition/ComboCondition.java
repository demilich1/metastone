package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class ComboCondition extends Condition {

	public ComboCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		return player.getHero().hasAttribute(Attribute.COMBO);
	}

}
