package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.custom.AdjacentMetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.PhysicalAttackTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class FoeReaper4000 extends MinionCard {

	public FoeReaper4000() {
		super("Foe Reaper 4000", 6, 9, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Also damages the minions next to whomever he attacks.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 514;
	}

	@Override
	public Minion summon() {
		Minion foeReaper4000 = createMinion();
		SpellDesc damage = DamageSpell.create((context, player, target) -> {
			Minion attacker = (Minion) context.getEnvironment().get(Environment.ATTACKER);
			return attacker.getAttack();
		});
		SpellDesc cleave = AdjacentMetaSpell.create(EntityReference.EVENT_TARGET, null, damage);
		SpellTrigger trigger = new SpellTrigger(new PhysicalAttackTrigger(false), cleave);
		foeReaper4000.setSpellTrigger(trigger);
		return foeReaper4000;
	}
}
