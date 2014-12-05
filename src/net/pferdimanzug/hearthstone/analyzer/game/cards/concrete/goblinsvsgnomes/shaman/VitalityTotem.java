package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class VitalityTotem extends MinionCard {

	public VitalityTotem() {
		super("Vitality Totem", 0, 3, Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("At the end of your turn, restore 4 Health to your hero.");
		setRace(Race.TOTEM);
	}

	@Override
	public Minion summon() {
		Minion vitalityTotem = createMinion();
		SpellDesc heal = HealingSpell.create(4);
		heal.setTarget(EntityReference.FRIENDLY_HERO);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), heal);
		vitalityTotem.setSpellTrigger(trigger);
		return vitalityTotem;
	}

}
