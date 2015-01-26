package net.demilich.metastone.game.spells.custom;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ConeOfColdSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ConeOfColdSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		List<Actor> affected = context.getAdjacentMinions(player, target.getReference());
		affected.add((Actor) target);

		SpellDesc damage = DamageSpell.create(1);
		damage.setSourceEntity(desc.getSourceEntity());
		SpellDesc freeze = ApplyTagSpell.create(GameTag.FROZEN);
		freeze.setSourceEntity(desc.getSourceEntity());

		for (Entity minion : affected) {
			damage.setTarget(minion.getReference());
			context.getLogic().castSpell(player.getId(), damage);
			freeze.setTarget(minion.getReference());
			context.getLogic().castSpell(player.getId(), freeze);
		}
	}

}