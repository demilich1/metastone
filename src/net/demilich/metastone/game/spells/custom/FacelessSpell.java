package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;

public class FacelessSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(FacelessSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion template = (Minion) target;
		Minion clone = template.clone();
		clone.setSpellTrigger(null);
		clone.setCardCostModifier(null);

		Minion sourceActor = context.getSummonStack().peek();
		SpellDesc transformSpell = TransformMinionSpell.create(clone);
		transformSpell.setTarget(sourceActor.getReference());
		transformSpell.setSourceEntity(desc.getSourceEntity());
		context.getLogic().castSpell(player.getId(), transformSpell);
		
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