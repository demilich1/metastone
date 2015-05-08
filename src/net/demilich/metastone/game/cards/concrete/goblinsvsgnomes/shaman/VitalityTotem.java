package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class VitalityTotem extends MinionCard {

	public VitalityTotem() {
		super("Vitality Totem", 0, 3, Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("At the end of your turn, restore 4 Health to your hero.");
		setRace(Race.TOTEM);
	}

	@Override
	public int getTypeId() {
		return 581;
	}



	@Override
	public Minion summon() {
		Minion vitalityTotem = createMinion();
		SpellDesc heal = HealingSpell.create(EntityReference.FRIENDLY_HERO, 4);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(null), heal);
		vitalityTotem.setSpellTrigger(trigger);
		return vitalityTotem;
	}
}
