package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ModifyMaxManaSpell extends Spell {
	
	public static SpellDesc create() {
		return create(1, TargetPlayer.SELF, false);
	}
	
	public static SpellDesc create(int value, TargetPlayer targetPlayer, boolean fullManaCrystals) {
		SpellDesc desc = new SpellDesc(ModifyMaxManaSpell.class);
		desc.setValue(value);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
		desc.set(SpellArg.FULL_MANA_CRYSTALS, fullManaCrystals);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int value = desc.getValue();
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		boolean fullManaCrystals = desc.getBool(SpellArg.FULL_MANA_CRYSTALS);
		
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case SELF:
			addMana(context, player, value, fullManaCrystals);
			break;
		case OPPONENT:
			addMana(context, opponent, value, fullManaCrystals);
			break;
		case BOTH:
			addMana(context, player, value, fullManaCrystals);
			addMana(context, opponent, value, fullManaCrystals);
			break;
		}
	}
	
	private void addMana(GameContext context, Player player, int value, boolean fullManaCrystals) {
		context.getLogic().modifyMaxMana(player, value);
		if (fullManaCrystals) {
			context.getLogic().modifyCurrentMana(player.getId(), value);
		}
	}

}
