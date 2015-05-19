package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class ImpGangBoss extends MinionCard {

	public ImpGangBoss() {
		super("Imp Gang Boss", 2, 4, Rarity.COMMON, HeroClass.WARLOCK, 3);
		setDescription("Whenever this minion takes damage, summon a 1/1 Imp.");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion impGangBoss = createMinion();
		//SpellDesc summonImp = SummonSpell.create(new Imp());
		SpellDesc summonImp = SummonSpell.create();
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(null), summonImp);
		impGangBoss.setSpellTrigger(trigger);
		return impGangBoss;
	}



	@Override
	public int getTypeId() {
		return 638;
	}
}
