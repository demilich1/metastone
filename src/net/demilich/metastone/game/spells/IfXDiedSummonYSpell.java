package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class IfXDiedSummonYSpell extends Spell {
	
	public static SpellDesc create(UniqueEntity x, MinionCard y) {
		Map<SpellArg, Object> arguments = SpellDesc.build(IfXDiedSummonYSpell.class);
		arguments.put(SpellArg.UNIQUE_MINION, x);
		arguments.put(SpellArg.CARD, y);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		UniqueEntity x = (UniqueEntity) desc.get(SpellArg.UNIQUE_MINION);
		MinionCard y = (MinionCard) desc.get(SpellArg.CARD);
		for (Entity deadEntity : player.getGraveyard()) {
			if (deadEntity.getTag(GameTag.UNIQUE_ENTITY) == x) {
				context.getLogic().summon(player.getId(), y.summon());
				break;
			}
		}
	}

}
