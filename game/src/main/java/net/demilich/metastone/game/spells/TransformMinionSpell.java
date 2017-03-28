package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.SummonCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class TransformMinionSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(TransformMinionSpell.class);

	public static SpellDesc create(EntityReference target, Minion transformTarget, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformMinionSpell.class);
		arguments.put(SpellArg.SECONDARY_TARGET, transformTarget);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(EntityReference target, String templateCard, boolean randomTarget) {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformMinionSpell.class);
		arguments.put(SpellArg.CARD, templateCard);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.RANDOM_TARGET, randomTarget);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(Minion transformTarget) {
		return create(null, transformTarget, false);
	}

	public static SpellDesc create(String templateCard) {
		return create(null, templateCard, false);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (target instanceof Hero) {
			String heroCardName = desc.getString(SpellArg.HERO_CARD);
			SpellDesc changeHeroSpell = ChangeHeroSpell.create(heroCardName);
			SpellUtils.castChildSpell(context, context.getPlayer(target.getOwner()), changeHeroSpell, source, target);
		} else {
			String cardName = (String) desc.get(SpellArg.CARD);
			Summon summon = (Summon) target;
			Summon transformTarget = (Summon) desc.get(SpellArg.SECONDARY_TARGET);
			
			SummonCard templateCard = cardName != null ? (SummonCard) context.getCardById(cardName) : null;
			Summon newSummon = transformTarget != null ? transformTarget : templateCard.summon();
			
			logger.debug("{} is transformed into a {}", summon, newSummon);
			context.getLogic().transformMinion(summon, newSummon);
		}
	}

}
