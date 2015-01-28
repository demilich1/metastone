package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Maexxna extends MinionCard {

	public Maexxna() {
		super("Maexxna", 2, 8, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Destroy any minion damaged by this minion.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 393;
	}

	@Override
	public Minion summon() {
		Minion maexxna = createMinion();
		SpellDesc killSpell = DestroySpell.create();
		killSpell.setTarget(EntityReference.EVENT_TARGET);
		maexxna.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(false), killSpell));
		return maexxna;
	}
}
