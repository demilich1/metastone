package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;

public class FacelessSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(FacelessSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion template = (Minion) target;
		Minion clone = template.clone();
		clone.removeAttribute(Attribute.AURA_ATTACK_BONUS);
		clone.removeAttribute(Attribute.AURA_HP_BONUS);
		clone.removeAttribute(Attribute.AURA_UNTARGETABLE_BY_SPELLS);
		clone.clearSpellTriggers();
		clone.setCardCostModifier(null);

		Minion sourceActor = (Minion) context.resolveSingleTarget(context.getSummonReferenceStack().peek());
		SpellDesc transformSpell = TransformMinionSpell.create(clone);
		if (context.getEnvironment().get(Environment.TRANSFORM_REFERENCE) != null) {
			SpellUtils.castChildSpell(context, player, transformSpell, source, sourceActor);
			return;
		}
		SpellUtils.castChildSpell(context, player, transformSpell, source, sourceActor);

		for (IGameEventListener trigger : context.getTriggersAssociatedWith(template.getReference())) {
			IGameEventListener triggerClone = trigger.clone();
			if (triggerClone instanceof CardCostModifier) {
				context.getLogic().addManaModifier(player, (CardCostModifier) triggerClone, clone);
			} else {
				context.getLogic().addGameEventListener(player, triggerClone, clone);
			}

		}
	}
}