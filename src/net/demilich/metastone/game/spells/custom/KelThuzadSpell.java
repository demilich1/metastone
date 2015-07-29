package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class KelThuzadSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(KelThuzadSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int currentTurn = context.getTurn();
		List<Entity> graveyardSnapshot = new ArrayList<>(player.getGraveyard());
		for (Entity deadEntity : graveyardSnapshot) {
			if (deadEntity.getEntityType() != EntityType.MINION) {
				continue;
			}
			Minion deadMinion = (Minion) deadEntity;
			if (deadMinion.getAttributeValue(Attribute.DIED_ON_TURN) == currentTurn) {
				MinionCard minionCard = (MinionCard) deadMinion.getSourceCard();
				context.getLogic().summon(player.getId(), minionCard.summon());
			}
		}
	}

}