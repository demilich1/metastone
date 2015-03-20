package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.DoubleAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Gahzrilla extends MinionCard {

	public Gahzrilla() {
		super("Gahz'rilla", 6, 9, Rarity.LEGENDARY, HeroClass.HUNTER, 7);
		setDescription("Whenever this minion takes damage, double its Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 487;
	}

	@Override
	public Minion summon() {
		Minion gahzrilla = createMinion();
		SpellDesc buffSpell = DoubleAttackSpell.create(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), buffSpell);
		gahzrilla.setSpellTrigger(trigger);
		return gahzrilla;
	}
}
