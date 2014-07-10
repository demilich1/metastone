package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

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

public class EmperorCobra extends MinionCard {

	public EmperorCobra() {
		super("Emperor Cobra", 2, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Destroy any minion damaged by this minion.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion emperorCobra = createMinion();
		Spell killSpell = new DestroySpell();
		killSpell.setTarget(EntityReference.EVENT_TARGET);
		emperorCobra.setSpellTrigger(new SpellTrigger(new PhysicalAttackTrigger(false), killSpell));
		return emperorCobra;
	}
	
	

}
