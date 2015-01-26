package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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