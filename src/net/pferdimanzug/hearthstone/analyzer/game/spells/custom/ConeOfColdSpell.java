package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;

public class ConeOfColdSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ConeOfColdSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		List<Entity> affected = context.getAdjacentMinions(player, target.getReference());
		affected.add((Actor) target);

		SpellDesc damage = DamageSpell.create(1);
		damage.setSource(SpellSource.SPELL_CARD);
		SpellDesc freeze = ApplyTagSpell.create(GameTag.FROZEN, new TurnStartTrigger());
		freeze.setSource(SpellSource.SPELL_CARD);

		for (Entity minion : affected) {
			damage.setTarget(minion.getReference());
			context.getLogic().castSpell(player.getId(), damage);
			freeze.setTarget(minion.getReference());
			context.getLogic().castSpell(player.getId(), freeze);
		}
	}

}