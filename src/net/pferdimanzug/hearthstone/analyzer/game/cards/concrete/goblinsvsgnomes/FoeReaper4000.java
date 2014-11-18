package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.AdjacentMetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.PhysicalAttackTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class FoeReaper4000 extends MinionCard {

	public FoeReaper4000() {
		super("Foe Reaper 4000", 6, 9, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Also damages the minions next to whomever he attacks.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion foeReaper4000 = createMinion();
		SpellDesc damage = DamageSpell.create((context, player, target) -> {
			Minion attacker = (Minion) context.getEnvironment().get(Environment.ATTACKER);
			return attacker.getAttack();
		});
		SpellDesc cleave = AdjacentMetaSpell.create(null, damage);
		cleave.setTarget(EntityReference.EVENT_TARGET);
		SpellTrigger trigger = new SpellTrigger(new PhysicalAttackTrigger(false), cleave);
		foeReaper4000.setSpellTrigger(trigger);
		return foeReaper4000;
	}

}
