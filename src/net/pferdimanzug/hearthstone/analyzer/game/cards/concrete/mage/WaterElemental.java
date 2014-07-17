package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class WaterElemental extends MinionCard {

	public WaterElemental() {
		super("Water Elemental", 3, 6, Rarity.FREE, HeroClass.MAGE, 4);
		setDescription("Freeze any character damaged by this minion.");
	}

	@Override
	public int getTypeId() {
		return 75;
	}



	@Override
	public Minion summon() {
		Minion waterElemental = createMinion();
		Spell freezeSpell = new ApplyTagSpell(GameTag.FROZEN);
		freezeSpell.setTarget(EntityReference.EVENT_TARGET);
		waterElemental.setSpellTrigger(new SpellTrigger(new PhysicalAttackTrigger(true), freezeSpell));
		return waterElemental;
	}
}
