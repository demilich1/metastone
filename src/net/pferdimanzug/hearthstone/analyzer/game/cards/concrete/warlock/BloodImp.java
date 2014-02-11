package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BloodImp extends MinionCard {

	public BloodImp() {
		super("Blood Imp", 0, 1, Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("Stealth. At the end of your turn, give another random friendly minion +1 Health.");
	} 
	
	@Override
	public Minion summon() {
		Minion bloodImp = createMinion(Race.DEMON, GameTag.STEALTHED);
		Spell spell = new BuffRandomSpell(0, 1);
		spell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), spell);
		bloodImp.setSpellTrigger(trigger);
		return bloodImp;
	}

}
