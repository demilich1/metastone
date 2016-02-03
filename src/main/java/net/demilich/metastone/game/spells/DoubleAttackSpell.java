package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DoubleAttackSpell extends Spell {

	public static SpellDesc create() {
		return create(null);
	}

	public static SpellDesc create(EntityReference target) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DoubleAttackSpell.class);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor targetActor = (Actor) target;
		targetActor.modifyAttribute(Attribute.ATTACK_BONUS, targetActor.getAttributeValue(Attribute.ATTACK) + targetActor.getAttributeValue(Attribute.ATTACK_BONUS));
		targetActor.modifyAttribute(Attribute.TEMPORARY_ATTACK_BONUS, targetActor.getAttributeValue(Attribute.TEMPORARY_ATTACK_BONUS));
	}

}
