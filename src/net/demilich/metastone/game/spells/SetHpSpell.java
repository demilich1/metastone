package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SetHpSpell extends Spell {

	public static SpellDesc create(int hp) {
		SpellDesc desc = new SpellDesc(SetHpSpell.class);
		desc.setValue(hp);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int hp = desc.getValue();
		Actor targetActor = (Actor) target;
		targetActor.removeTag(GameTag.HP_BONUS);
		context.getLogic().modifyMaxHp(targetActor, hp);
	}

}
