package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Maexxna extends MinionCard {

	public Maexxna() {
		super("Maexxna", 2, 8, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Destroy any minion damaged by this minion.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion maexxna = createMinion();
		Spell killSpell = new DestroySpell();
		killSpell.setTarget(EntityReference.EVENT_TARGET);
		maexxna.setSpellTrigger(new SpellTrigger(new PhysicalAttackTrigger(false), killSpell));
		return maexxna;
	}

}
