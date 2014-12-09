package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		targetActor.setMaxHp(hp);
		targetActor.setHp(hp);
	}

}
