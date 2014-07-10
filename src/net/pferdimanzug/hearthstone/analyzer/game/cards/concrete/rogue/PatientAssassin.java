package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class PatientAssassin extends MinionCard {

	public PatientAssassin() {
		super("Patient Assassin", 1, 1, Rarity.EPIC, HeroClass.ROGUE, 2);
		setDescription("Stealth. Destroy any minion damaged by this minion.");
	}

	@Override
	public Minion summon() {
		Minion patientAssassin = createMinion(GameTag.STEALTHED);
		Spell killSpell = new DestroySpell();
		killSpell.setTarget(EntityReference.EVENT_TARGET);
		patientAssassin.setSpellTrigger(new SpellTrigger(new PhysicalAttackTrigger(false), killSpell));
		return patientAssassin;
	}

}
