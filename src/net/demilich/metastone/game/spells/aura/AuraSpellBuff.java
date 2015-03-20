package net.demilich.metastone.game.spells.aura;

import java.util.Map;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuraSpellBuff extends Spell {

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}
	
	public static SpellDesc create(int attackBonus, int hpBonus) {
		return create(attackBonus, hpBonus, null);
	}
	
	public static SpellDesc create(int attackBonus, int hpBonus, Predicate<Entity> targetFilter) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AuraSpellBuff.class);
		arguments.put(SpellArg.ATTACK_BONUS, attackBonus);
		arguments.put(SpellArg.HP_BONUS, hpBonus);
		if (targetFilter != null) {
			arguments.put(SpellArg.ENTITY_FILTER, targetFilter);	
		}
		
		return new SpellDesc(arguments);
	}
	
	private static Logger logger = LoggerFactory.getLogger(AuraSpellBuff.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int hpBonus = desc.getInt(SpellArg.HP_BONUS);
		Actor targetActor = (Actor) target;
		logger.debug("{} gains ({} from aura effect)", targetActor, attackBonus + "/" + hpBonus);
		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.AURA_ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyAuraHpBonus(hpBonus);
		}

	}
}
