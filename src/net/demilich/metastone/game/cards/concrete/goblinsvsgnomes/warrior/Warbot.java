package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.EnrageSpell;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class Warbot extends MinionCard {

	public Warbot() {
		super("Warbot", 1, 3, Rarity.COMMON, HeroClass.WARRIOR, 1);
		setDescription("Enrage: +1 Attack.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 610;
	}

	@Override
	public Minion summon() {
		Minion warbot = createMinion(GameTag.ENRAGABLE);
		SpellTrigger trigger = new SpellTrigger(new EnrageChangedTrigger(), EnrageSpell.create(+1));
		warbot.setSpellTrigger(trigger);
		return warbot;
	}
}
