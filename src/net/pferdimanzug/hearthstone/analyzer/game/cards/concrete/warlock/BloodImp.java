package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc spell = BuffSpell.create(0, 1);
		spell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		spell.pickRandomTarget(true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), spell);
		bloodImp.setSpellTrigger(trigger);
		return bloodImp;
	}
}
