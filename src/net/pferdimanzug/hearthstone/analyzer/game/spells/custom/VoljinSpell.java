package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		voljin.setHp(targetHp);
		voljin.setMaxHp(targetHp);
		voljin.setTag(GameTag.HP_BONUS, 0);
		targetActor.setHp(sourceHp);
		targetActor.setMaxHp(sourceHp);
		targetActor.setTag(GameTag.HP_BONUS, 0);
	}

}
