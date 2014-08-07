package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
