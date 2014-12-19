package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.CardCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;

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