package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.LifeLeechSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class MistressOfPain extends MinionCard {

	public MistressOfPain() {
		super("Mistress of Pain", 1, 4, Rarity.RARE, HeroClass.WARLOCK, 2);
		setDescription("Whenever this minion deals damage, restore that much Health to your hero.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 602;
	}

	@Override
	public Minion summon() {
		Minion mistressOfPain = createMinion();
		SpellDesc lifeLeech = LifeLeechSpell.create();
		//SpellTrigger trigger = new SpellTrigger(new PhysicalAttackTrigger(true), lifeLeech);
		SpellTrigger trigger = new SpellTrigger(new DamageCausedTrigger(null), lifeLeech);
		mistressOfPain.setSpellTrigger(trigger);
		return mistressOfPain;
	}
}
