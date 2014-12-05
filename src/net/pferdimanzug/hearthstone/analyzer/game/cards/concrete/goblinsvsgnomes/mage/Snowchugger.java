package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Snowchugger extends MinionCard {

	public Snowchugger() {
		super("Snowchugger", 2, 3, Rarity.COMMON, HeroClass.MAGE, 2);
		setDescription("Freeze any character damaged by this minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 496;
	}



	@Override
	public Minion summon() {
		Minion snowchugger = createMinion();
		SpellDesc freezeSpell = ApplyTagSpell.create(GameTag.FROZEN);
		freezeSpell.setTarget(EntityReference.EVENT_TARGET);
		snowchugger.setSpellTrigger(new SpellTrigger(new PhysicalAttackTrigger(true), freezeSpell));
		return snowchugger;
	}
}
