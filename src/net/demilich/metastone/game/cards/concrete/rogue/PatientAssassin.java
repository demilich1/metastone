package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class PatientAssassin extends MinionCard {

	public PatientAssassin() {
		super("Patient Assassin", 1, 1, Rarity.EPIC, HeroClass.ROGUE, 2);
		setDescription("Stealth. Destroy any minion damaged by this minion.");
	}

	@Override
	public int getTypeId() {
		return 299;
	}

	@Override
	public Minion summon() {
		Minion patientAssassin = createMinion(GameTag.STEALTH);
		SpellDesc killSpell = DestroySpell.create(EntityReference.EVENT_TARGET);
		//patientAssassin.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(false), killSpell));
		patientAssassin.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(null), killSpell));
		return patientAssassin;
	}
}
