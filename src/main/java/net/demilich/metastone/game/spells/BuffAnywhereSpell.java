package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class BuffAnywhereSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffAnywhereSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue(SpellArg.ATTACK_BONUS, context, player, target, source, 0);
		int hpBonus = desc.getValue(SpellArg.HP_BONUS, context, player, target, source, 0);
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		if (value != 0) {
			attackBonus = hpBonus = value;
		}
		
		String[] cardNames = { desc.getString(SpellArg.CARD) };
		if (desc.get(SpellArg.CARDS) != null) {
			cardNames = (String[]) desc.get(SpellArg.CARDS);
		}
		for (String cardName : cardNames) {
			List<Entity> entities = new ArrayList<Entity>();
			for (Minion minion : player.getMinions()) {
				if (minion.getSourceCard().getCardId().equalsIgnoreCase(cardName)) {
					entities.add(minion);
				}
			}
			if (player.getHero().getWeapon() != null && player.getHero().getWeapon().getSourceCard().getCardId().equalsIgnoreCase(cardName)) {
				entities.add(player.getHero().getWeapon());
			}
			for (Card card : player.getHand()) {
				if (card.getCardId().equalsIgnoreCase(cardName)) {
					entities.add(card);
				}
			}
			for (Card card : player.getDeck()) {
				if (card.getCardId().equalsIgnoreCase(cardName)) {
					entities.add(card);
				}
			}
			for (Entity entity : entities) {
				logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);
				entity.modifyAttribute(Attribute.ATTACK_BONUS, attackBonus);
				entity.modifyHpBonus(hpBonus);
			}
		}
	}

}
