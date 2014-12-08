package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class SwapAttackAndHpSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(SwapAttackAndHpSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor targetActor = (Actor) target;
		
		int attack = targetActor.getAttack();
		int hp = targetActor.getHp();
		targetActor.setAttack(hp);
		targetActor.setMaxHp(attack);
		targetActor.setHp(attack);
		targetActor.removeTag(GameTag.TEMPORARY_ATTACK_BONUS);
		targetActor.removeTag(GameTag.ATTACK_BONUS);
	}

}