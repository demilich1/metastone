package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BuffWhenRaceIsOnBoardSpell extends Spell {

	public static SpellDesc create(Race race, int attackBonus) {
		SpellDesc desc = new SpellDesc(BuffWhenRaceIsOnBoardSpell.class);
		desc.set(SpellArg.RACE, race);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor targetActor = (Actor) target;
		targetActor.setTag(GameTag.CONDITIONAL_ATTACK_BONUS, 0);
		List<Entity> allMinions = context.resolveTarget(player, targetActor, EntityReference.FRIENDLY_MINIONS);
		Race race = (Race) desc.get(SpellArg.RACE);
		int attackBonus = 0;
		for (Entity entity : allMinions) {
			Minion minion = (Minion) entity;
			if (minion == targetActor) {
				continue;
			}
			if (minion.getRace() == race) {
				attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
				break;
			}
		}
		targetActor.modifyTag(GameTag.CONDITIONAL_ATTACK_BONUS, attackBonus);
	}

}
