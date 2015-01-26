package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc freezeSpell = ApplyTagSpell.create(GameTag.FROZEN);
		freezeSpell.setTarget(EntityReference.EVENT_TARGET);
		waterElemental.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(true), freezeSpell));
		return waterElemental;
	}
}
