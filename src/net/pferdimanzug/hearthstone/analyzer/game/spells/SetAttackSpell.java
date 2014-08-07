package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class SetAttackSpell extends Spell {

	public static SpellDesc create(int attack) {
		SpellDesc desc = new SpellDesc(SetAttackSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attack);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attack = desc.getInt(SpellArg.ATTACK_BONUS);
		Actor targetActor = (Actor) target;
		targetActor.modifyTag(GameTag.ATTACK_BONUS, -targetActor.getAttack() + attack);
	}
}
