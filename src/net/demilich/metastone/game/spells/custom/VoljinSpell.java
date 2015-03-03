package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class VoljinSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(VoljinSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion voljin = context.getSummonStack().peek();
		Actor targetActor = (Actor) target;
		int sourceHp = voljin.getHp();
		int targetHp = targetActor.getHp();
		context.getLogic().modifyMaxHp(voljin, targetHp);
		voljin.setTag(GameTag.HP_BONUS, 0);
		context.getLogic().modifyMaxHp(targetActor, sourceHp);
		targetActor.setTag(GameTag.HP_BONUS, 0);
	}

}
