package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class KelThuzadSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(KelThuzadSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int currentTurn = context.getTurn();
		List<Entity> graveyardSnapshot = new ArrayList<>(player.getGraveyard());
		for (Entity deadEntity : graveyardSnapshot) {
			if (deadEntity.getEntityType() != EntityType.MINION) {
				continue;
			}
			Minion deadMinion = (Minion) deadEntity;
			if (deadMinion.getTagValue(GameTag.DIED_ON_TURN) == currentTurn) {
				MinionCard minionCard = (MinionCard) deadMinion.getSourceCard();
				context.getLogic().summon(player.getId(), minionCard.summon());
			}
		}
	}
	
}