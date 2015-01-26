package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		context.getLogic().summon(player.getId(), minion);
	}

}
