package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Demolisher extends MinionCard {

	public Demolisher() {
		super("Demolisher", 1, 4, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the start of your turn, deal 2 damage to a random enemy.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 116;
	}

	@Override
	public Minion summon() {
		Minion demolisher = createMinion();
		SpellDesc randomDamageSpell = DamageRandomSpell.create(2, 1);
		randomDamageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), randomDamageSpell);
		demolisher.setSpellTrigger(trigger);
		return demolisher;
	}
}
