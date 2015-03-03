package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SwapAttackAndHpSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(SwapAttackAndHpSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		int attack = minion.getAttack();
		int hp = minion.getHp();
		minion.setAttack(hp);
		context.getLogic().modifyMaxHp(minion, attack);
		minion.removeTag(GameTag.TEMPORARY_ATTACK_BONUS);
		minion.removeTag(GameTag.ATTACK_BONUS);
	}

}