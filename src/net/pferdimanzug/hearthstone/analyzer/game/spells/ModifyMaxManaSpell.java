package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ModifyMaxManaSpell extends Spell {
	
	public static SpellDesc create() {
		return create(1, TargetPlayer.SELF);
	}
	
	public static SpellDesc create(int value, TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(ModifyMaxManaSpell.class);
		desc.set(SpellArg.MANA, value);
		desc.setTargetPlayer(targetPlayer);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int value = desc.getInt(SpellArg.MANA);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case SELF:
			context.getLogic().modifyMaxMana(player, value);
			break;
		case OPPONENT:
			context.getLogic().modifyMaxMana(opponent, value);
			break;
		case BOTH:
			context.getLogic().modifyMaxMana(player, value);
			context.getLogic().modifyMaxMana(opponent, value);
			break;
		}

	}

}
