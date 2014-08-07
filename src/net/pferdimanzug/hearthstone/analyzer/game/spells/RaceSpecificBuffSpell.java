package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class RaceSpecificBuffSpell extends BuffSpell {
	
	public static SpellDesc create(int attackBonus, int hpBonus, Race race) {
		SpellDesc desc = BuffSpell.create(attackBonus, hpBonus);
		desc.setSpellClass(RaceSpecificBuffSpell.class);
		desc.set(SpellArg.RACE, race);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Race race = (Race) desc.get(SpellArg.RACE);
		Minion minion = (Minion) target;
		if (minion.getRace() != race) {
			return;
		}
		
		super.onCast(context, player, desc, target);
	}
	

}
