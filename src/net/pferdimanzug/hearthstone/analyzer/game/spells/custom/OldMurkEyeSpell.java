package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OldMurkEyeSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(OldMurkEyeSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor targetActor = (Actor) target;
		targetActor.setTag(GameTag.ATTACK_BONUS, 0);
		List<Entity> allMinions = context.resolveTarget(player, targetActor, EntityReference.ALL_MINIONS);
		int attackBonus = 0;
		for (Entity entity : allMinions) {
			Minion minion = (Minion) entity;
			// Old Murk-Eye is a Murloc himself, but should not count towards
			// attack
			if (minion == targetActor) {
				continue;
			}
			if (minion.getRace() == Race.MURLOC) {
				attackBonus++;
			}
		}
		targetActor.modifyTag(GameTag.ATTACK_BONUS, attackBonus);
	}

}
