package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.LifeLeechSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MistressOfPain extends MinionCard {

	public MistressOfPain() {
		super("Mistress of Pain", 1, 4, Rarity.RARE, HeroClass.WARLOCK, 2);
		setDescription("Whenever this minion deals damage, restore that much Health to your hero.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 602;
	}



	@Override
	public Minion summon() {
		Minion mistressOfPain = createMinion();
		SpellDesc lifeLeech = LifeLeechSpell.create();
		lifeLeech.setTarget(EntityReference.FRIENDLY_HERO);
		SpellTrigger trigger = new SpellTrigger(new PhysicalAttackTrigger(true), lifeLeech);
		mistressOfPain.setSpellTrigger(trigger);
		return mistressOfPain;
	}
}
