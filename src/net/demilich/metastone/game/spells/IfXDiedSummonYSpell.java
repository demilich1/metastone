package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class IfXDiedSummonYSpell extends Spell {

	public static SpellDesc create(UniqueEntity x, MinionCard y) {
		SpellDesc desc = new SpellDesc(IfXDiedSummonYSpell.class);
		desc.set(SpellArg.UNIQUE_MINION, x);
		desc.set(SpellArg.CARD, y);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
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
