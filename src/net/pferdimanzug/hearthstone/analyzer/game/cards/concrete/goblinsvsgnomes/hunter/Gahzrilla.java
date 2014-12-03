package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.DoubleAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Gahzrilla extends MinionCard {

	public Gahzrilla() {
		super("Gahz'rilla", 6, 9, Rarity.LEGENDARY, HeroClass.HUNTER, 7);
		setDescription("Whenever this minion takes damage, double its Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion gahzrilla = createMinion();
		SpellDesc buffSpell = DoubleAttackSpell.create();
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), buffSpell);
		gahzrilla.setSpellTrigger(trigger);
		return gahzrilla;
	}

}
