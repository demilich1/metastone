package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class BloodImp extends MinionCard {

	public BloodImp() {
		super("Blood Imp", 0, 1, Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("Stealth. At the end of your turn, give another random friendly minion +1 Health.");
		setRace(Race.DEMON);
	} 
	
	@Override
	public int getTypeId() {
		return 335;
	}

	@Override
	public Minion summon() {
		Minion bloodImp = createMinion(GameTag.STEALTHED);
		SpellDesc spell = BuffSpell.create(EntityReference.OTHER_FRIENDLY_MINIONS, 0, 1, true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), spell);
		bloodImp.setSpellTrigger(trigger);
		return bloodImp;
	}
}
