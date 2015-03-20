package net.demilich.metastone.game.spells;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffWhenRaceIsOnBoardSpell extends Spell {
	
	public static SpellDesc create(Race race, int attackBonus) {
		return create(null, race, attackBonus);
	}

	public static SpellDesc create(EntityReference target, Race race, int attackBonus) {
		Map<SpellArg, Object> arguments = SpellDesc.build(BuffWhenRaceIsOnBoardSpell.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.RACE, race);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
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
