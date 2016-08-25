package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ClearOverloadSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(ClearOverloadSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int lockedMana = player.getLockedMana();
		if (lockedMana > 0) {
			context.getLogic().modifyCurrentMana(player.getId(), lockedMana);
			player.setLockedMana(0);
		}

		player.removeAttribute(Attribute.OVERLOAD);
	}

}
