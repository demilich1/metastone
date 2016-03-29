package net.demilich.metastone.game.spells.custom;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class HeraldVolajzSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(HeraldVolajzSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		List<Entity> otherMinions = context.resolveTarget(player, source, EntityReference.OTHER_FRIENDLY_MINIONS);
		for (Entity entity : otherMinions) {
			Minion minion = (Minion) entity;
			MinionCard minionCard = (MinionCard) minion.getSourceCard();
			minion = minionCard.summon();
			if (context.getLogic().summon(player.getId(), minion)) {
				minion.setAttack(1);
				minion.setHp(1);
				minion.setMaxHp(1);
			}
		}
	}

}