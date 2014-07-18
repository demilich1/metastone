package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class PintSizedSummoner extends MinionCard {

	public PintSizedSummoner() {
		super("Pint-Sized Summoner", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("The first minion you play each turn costs (1) less.");
	}

	@Override
	public int getTypeId() {
		return 182;
	}

	@Override
	public Minion summon() {
		Minion pintSizedSummoner = createMinion();
		//TODO: this does not work correctly, the bonus cost is only really active for the very
		// first minion you play this turn. So when you play the Pint-Sized, you can only benefit next
		// turn, because this turn the Pint-Sized itself was the minion which was played first
		pintSizedSummoner.setTag(GameTag.ONE_TIME_MINION_MANA_COST, -1);
		Spell cheaperMinions = new ModifyTagSpell(GameTag.ONE_TIME_MINION_MANA_COST,-1);
		cheaperMinions.setTarget(EntityReference.SELF);
		pintSizedSummoner.setSpellTrigger(new SpellTrigger(new TurnStartTrigger(), cheaperMinions));
		return pintSizedSummoner;
	}
}
