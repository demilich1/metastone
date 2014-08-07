package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
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

		Minion sourceActor = context.getSummonStack().peek();
		SpellDesc transformSpell = TransformMinionSpell.create(clone);
		transformSpell.setTarget(sourceActor.getReference());
		context.getLogic().castSpell(player.getId(), transformSpell);
		for (IGameEventListener trigger : context.getTriggersAssociatedWith(template.getReference())) {
			IGameEventListener triggerClone = trigger.clone();
			context.getLogic().addGameEventListener(player, triggerClone, clone);
		}
	}
}