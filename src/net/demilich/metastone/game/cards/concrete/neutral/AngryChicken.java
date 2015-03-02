package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.EnrageSpell;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class AngryChicken extends MinionCard {

	public AngryChicken() {
		super("Angry Chicken", 1, 1, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Enrage: +5 Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 86;
	}

	@Override
	public Minion summon() {
		Minion angryChicken = createMinion(GameTag.ENRAGABLE);
		SpellTrigger trigger = new SpellTrigger(new EnrageChangedTrigger(), EnrageSpell.create(+5));
		angryChicken.setSpellTrigger(trigger);
		return angryChicken;
	}
}
