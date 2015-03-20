package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ModifyMaxManaSpell extends Spell {

	public static SpellDesc create() {
		return create(1, false);
	}
	
	public static SpellDesc create(int value, boolean fullManaCrystals) {
		return create(TargetPlayer.SELF, value, fullManaCrystals);
	}
	
	public static SpellDesc create(TargetPlayer targetPlayer, int value, boolean fullManaCrystals) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ModifyMaxManaSpell.class);
		arguments.put(SpellArg.VALUE, value);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		arguments.put(SpellArg.FULL_MANA_CRYSTALS, fullManaCrystals);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int value = desc.getValue();
		boolean fullManaCrystals = desc.getBool(SpellArg.FULL_MANA_CRYSTALS);

		context.getLogic().modifyMaxMana(player, value);
		if (fullManaCrystals) {
			context.getLogic().modifyCurrentMana(player.getId(), value);
		}
	}

}
