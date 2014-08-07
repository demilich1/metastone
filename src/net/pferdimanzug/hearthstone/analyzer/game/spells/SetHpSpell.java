package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class SetHpSpell extends Spell {

	public static SpellDesc create(int hp) {
		SpellDesc desc = new SpellDesc(SetHpSpell.class);
		desc.set(SpellArg.HP_BONUS, hp);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int hp = desc.getInt(SpellArg.HP_BONUS);
		Actor targetActor = (Actor) target;
		targetActor.modifyHpBonus(-targetActor.getHp() + hp);
	}

}
