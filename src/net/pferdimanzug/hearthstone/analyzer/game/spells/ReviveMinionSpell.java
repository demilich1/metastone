package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ReviveMinionSpell extends Spell {
	
	public static SpellDesc create() {
		return create(0);
	}
	
	public static SpellDesc create(int hpAdjustment) {
		SpellDesc desc = new SpellDesc(ReviveMinionSpell.class);
		desc.set(SpellArg.HP_BONUS, hpAdjustment);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int hpAdjustment = desc.getInt(SpellArg.HP_BONUS);
		Actor targetActor = (Actor) target;
		MinionCard minionCard = (MinionCard) targetActor.getSourceCard();
		Minion minion = minionCard.summon();
		if (hpAdjustment != 0) {
			minion.setHp(hpAdjustment);
		}
		context.getLogic().summon(player.getId(), minion, null, null, false);
	}

}
