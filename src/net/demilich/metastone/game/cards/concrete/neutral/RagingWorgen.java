package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EnrageSpell;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class RagingWorgen extends MinionCard {

	public RagingWorgen() {
		super("Raging Worgen", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Enrage: Windfury and +1 Attack");
	}

	@Override
	public int getTypeId() {
		return 185;
	}

	@Override
	public Minion summon() {
		Minion ragingWorgen = createMinion(GameTag.ENRAGABLE);
		SpellTrigger trigger = new SpellTrigger(new EnrageChangedTrigger(), EnrageSpell.create(+6, GameTag.WINDFURY));
		ragingWorgen.setSpellTrigger(trigger);
		return ragingWorgen;
	}
}
