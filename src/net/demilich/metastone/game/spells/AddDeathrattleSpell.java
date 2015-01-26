package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AddDeathrattleSpell extends Spell {
	
	public static SpellDesc create(SpellDesc deathrattle) {
		SpellDesc desc = new SpellDesc(AddDeathrattleSpell.class);
		desc.set(SpellArg.DEATHRATTLE, deathrattle);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor minion = (Actor) target;
		SpellDesc deathrattle = (SpellDesc) desc.get(SpellArg.DEATHRATTLE);
		minion.addDeathrattle(deathrattle);
	}

}
